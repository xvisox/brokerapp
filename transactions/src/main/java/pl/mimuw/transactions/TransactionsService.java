package pl.mimuw.transactions;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.mimuw.transactions.clients.UsersClient;
import pl.mimuw.transactions.exceptions.InvalidSharesAmountException;
import pl.mimuw.transactions.exceptions.InvalidTickerException;
import pl.mimuw.transactions.models.Share;
import pl.mimuw.transactions.payload.QuoteDataDto;
import pl.mimuw.transactions.payload.BuyStockDto;
import pl.mimuw.transactions.payload.SellStockDto;
import reactor.core.publisher.Mono;

@Data
@Service
@RequiredArgsConstructor
public class TransactionsService {
    private final WebClient finhubClient;
    private final UsersClient usersClient;
    private final ShareRepository shareRepo;

    @Value("${secret.api.key}")
    private String token;
    private final String API_URL_TEMPLATE = "/api/v1/quote?symbol=%s&token=%s";

    public String buyStocks(BuyStockDto buyStockDto, String token) throws RuntimeException {
        String username = validateAndGetUsername(token);
        Double stockPrice = getRequestedStockPrice(buyStockDto.getTicker());
        // Update user's wallet, this will throw an exception if user doesn't have enough money.
        usersClient.decreaseBalance(username, stockPrice * buyStockDto.getAmount());

        // Aggregate shares with the same ticker and shareholderId.
        Share share = getShare(username, buyStockDto.getTicker());
        if (share != null) {
            share.setAmount(share.getAmount() + buyStockDto.getAmount());
        } else {
            share = Share.builder()
                    .shareholderName(username)
                    .ticker(buyStockDto.getTicker())
                    .amount(buyStockDto.getAmount())
                    .build();
        }
        shareRepo.save(share);

        // TODO: asynchronously update user's transactions history
        return getMessage("Bought", buyStockDto.getAmount(), stockPrice);
    }

    public String sellStocks(SellStockDto sellStockDto, String token) throws RuntimeException {
        String username = validateAndGetUsername(token);
        // Check if user has enough stocks to sell.
        Share share = getShare(username, sellStockDto.getTicker());
        if (share == null || share.getAmount() < sellStockDto.getAmount()) {
            throw new InvalidSharesAmountException("You don't have enough stocks to sell");
        }

        // Update user's wallet.
        Double stockPrice = getRequestedStockPrice(sellStockDto.getTicker());
        usersClient.increaseBalance(username, stockPrice * sellStockDto.getAmount());

        // Sell stocks.
        share.setAmount(share.getAmount() - sellStockDto.getAmount());
        shareRepo.save(share);

        // TODO: asynchronously update user's transactions history
        return getMessage("Sold", sellStockDto.getAmount(), stockPrice);
    }

    private Double getRequestedStockPrice(String ticker) throws InvalidTickerException {
        String request = String.format(API_URL_TEMPLATE, ticker, token);
        Mono<QuoteDataDto> result = finhubClient.get()
                .uri(request)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(QuoteDataDto.class);
        QuoteDataDto quoteDataDto = result.block();
        if (quoteDataDto == null || quoteDataDto.getC() == 0) {
            throw new InvalidTickerException("No data for ticker: " + ticker);
        }
        return quoteDataDto.getC();
    }

    private Share getShare(String shareholderName, String ticker) {
        return shareRepo.findByShareholderNameAndTicker(shareholderName, ticker);
    }

    private String getMessage(String action, Integer amount, Double stockPrice) {
        return "%s %d stocks for %s $".formatted(action, amount, stockPrice * amount);
    }

    private String validateAndGetUsername(String token) throws RuntimeException {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new RuntimeException("Not token provided");
        }
        try {
            ResponseEntity<String> response = usersClient.validate(token.substring(7));
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Invalid token");
        }
    }
}

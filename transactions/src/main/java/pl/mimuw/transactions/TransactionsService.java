package pl.mimuw.transactions;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
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
    private final WebClient webClient;
    private final ShareRepository shareRepo;

    @Value("${secret.api.key}")
    private String token;
    private final String API_URL_TEMPLATE = "/api/v1/quote?symbol=%s&token=%s";

    public String buyStocks(BuyStockDto buyStockDto) throws RuntimeException {
        Double stockPrice = getRequestedStockPrice(buyStockDto.getTicker());
        // TODO: synchronously update user's wallet here

        // Aggregate shares with the same ticker and shareholderId.
        Share share = getShare(buyStockDto.getShareholderId(), buyStockDto.getTicker());
        if (share != null) {
            share.setAmount(share.getAmount() + buyStockDto.getAmount());
        } else {
            share = Share.builder()
                    .shareholderId(buyStockDto.getShareholderId())
                    .ticker(buyStockDto.getTicker())
                    .amount(buyStockDto.getAmount())
                    .build();
        }
        shareRepo.save(share);

        // TODO: asynchronously update user's transactions history
        return getMessage("Bought", buyStockDto.getAmount(), stockPrice);
    }

    public String sellStocks(SellStockDto sellStockDto) throws RuntimeException {
        // Check if user has enough stocks to sell.
        Share share = getShare(sellStockDto.getShareholderId(), sellStockDto.getTicker());
        if (share == null || share.getAmount() < sellStockDto.getAmount()) {
            throw new InvalidSharesAmountException("You don't have enough stocks to sell");
        }

        Double stockPrice = getRequestedStockPrice(sellStockDto.getTicker());
        // TODO: synchronously update user's wallet here

        // Sell stocks.
        share.setAmount(share.getAmount() - sellStockDto.getAmount());
        shareRepo.save(share);

        // TODO: asynchronously update user's transactions history
        return getMessage("Sold", sellStockDto.getAmount(), stockPrice);
    }

    private Double getRequestedStockPrice(String ticker) throws InvalidTickerException {
        String request = String.format(API_URL_TEMPLATE, ticker, token);
        Mono<QuoteDataDto> result = webClient.get()
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

    private Share getShare(Long shareholderId, String ticker) {
        return shareRepo.findByShareholderIdAndTicker(shareholderId, ticker);
    }

    private String getMessage(String action, Integer amount, Double stockPrice) {
        return "%s %d stocks for %s $".formatted(action, amount, stockPrice * amount);
    }
}

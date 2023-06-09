package pl.mimuw.history;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.mimuw.history.clients.UsersClient;
import pl.mimuw.history.models.Transaction;
import pl.mimuw.history.payload.HistoricalTransactionDto;
import pl.mimuw.history.payload.HistoricalTransactionDtoMapper;

import java.util.List;

@Data
@Service
@RequiredArgsConstructor
public class HistoryService {
    private final UsersClient usersClient;
    private final TransactionRepository transactionRepo;

    public List<HistoricalTransactionDto> getHistoricalTransactions(String token) {
        String username = validateAndGetUsername(token);
        List<Transaction> transactions = transactionRepo.findByShareholderName(username);
        return HistoricalTransactionDtoMapper.mapTransactionListToHistoricalTransactionDtoList(transactions);
    }

    private String validateAndGetUsername(String token) throws RuntimeException {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new RuntimeException("No token provided");
        }
        try {
            ResponseEntity<String> response = usersClient.validate(token.substring(7));
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Invalid token");
        }
    }
}

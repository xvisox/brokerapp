package pl.mimuw.history.payload;

import pl.mimuw.history.models.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HistoricalTransactionDtoMapper {
    public static List<HistoricalTransactionDto> mapTransactionListToHistoricalTransactionDtoList(
            List<Transaction> transactions
    ) {
        return transactions.stream()
                .map(transaction -> HistoricalTransactionDto.builder()
                        .date(transaction.getDate())
                        .type(transaction.getType())
                        .ticker(transaction.getTicker())
                        .amount(transaction.getAmount())
                        .stockPrice(transaction.getStockPrice())
                        .totalValue(transaction.getTotalValue())
                        .remainingBalance(transaction.getRemainingBalance())
                        .build())
                .collect(Collectors.toCollection(ArrayList::new));
    }
}

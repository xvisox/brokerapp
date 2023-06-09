package pl.mimuw.history.payload;

import pl.mimuw.history.models.Transaction;

public class TransactionDtoMapper {

    public static Transaction mapTransactionDtoToTransaction(TransactionDto transactionDto) {
        return Transaction.builder()
                .date(transactionDto.getDate())
                .type(transactionDto.getType())
                .shareholderName(transactionDto.getShareholderName())
                .ticker(transactionDto.getTicker())
                .amount(transactionDto.getAmount())
                .stockPrice(transactionDto.getStockPrice())
                .totalValue(transactionDto.getTotalValue())
                .remainingBalance(transactionDto.getRemainingBalance())
                .build();
    }
}

package pl.mimuw.transactions.payload;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class TransactionDto {
    private Date date;
    private TransactionType type;
    private String shareholderName;
    private String ticker;
    private Integer amount;
    private Double totalValue;
    private Double remainingBalance;
}

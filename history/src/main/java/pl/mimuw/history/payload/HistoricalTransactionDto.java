package pl.mimuw.history.payload;

import lombok.Builder;
import lombok.Data;
import pl.mimuw.history.models.TransactionType;

import java.util.Date;

@Data
@Builder
public class HistoricalTransactionDto {
    private Date date;
    private TransactionType type;
    private String ticker;
    private Integer amount;
    private Double stockPrice;
    private Double totalValue;
    private Double remainingBalance;
}

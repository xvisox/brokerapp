package pl.mimuw.transactions.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    private Date date;
    private TransactionType type;
    private String shareholderName;
    private String ticker;
    private Integer amount;
    private Double stockPrice;
    private Double totalValue;
    private Double remainingBalance;
}

package pl.mimuw.history.payload;

import lombok.*;
import pl.mimuw.history.models.TransactionType;

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

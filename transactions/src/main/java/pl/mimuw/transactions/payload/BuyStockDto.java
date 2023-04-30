package pl.mimuw.transactions.payload;

import lombok.Data;

@Data
public class BuyStockDto {
    private Long shareholderId;
    private String ticker;
    private Integer amount;
}

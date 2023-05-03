package pl.mimuw.transactions.payload;

import lombok.Data;

@Data
public class BuyStockDto {
    private String ticker;
    private Integer amount;
}

package pl.mimuw.transactions.payload;

import lombok.Data;

@Data
public class SellStockDto {
    private String ticker;
    private Integer amount;
}

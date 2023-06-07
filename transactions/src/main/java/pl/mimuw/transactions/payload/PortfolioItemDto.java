package pl.mimuw.transactions.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PortfolioItemDto {
    private String ticker;
    private Integer amount;
}

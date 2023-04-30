package pl.mimuw.transactions.payload;

import lombok.Data;

@Data
public class QuoteDataDto {
    private Double c;   // current price
    private Double d;   // change in price
    private Double dp;  // percent change in price
    private Double h;   // high price
    private Double l;   // low price
    private Double o;   // open price
    private Double pc;  // previous close price
    private Long t;     // timestamp
}

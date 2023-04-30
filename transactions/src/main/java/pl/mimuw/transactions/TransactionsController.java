package pl.mimuw.transactions;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mimuw.transactions.payload.BuyStockDto;
import pl.mimuw.transactions.payload.SellStockDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionsController {
    private final TransactionsService transactionsService;

    @PostMapping("/buy")
    public ResponseEntity<?> buyStocks(@RequestBody BuyStockDto buyStockDto) {
        try {
            String message = transactionsService.buyStocks(buyStockDto);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/sell")
    public ResponseEntity<?> sellStocks(@RequestBody SellStockDto sellStockDto) {
        try {
            String message = transactionsService.sellStocks(sellStockDto);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

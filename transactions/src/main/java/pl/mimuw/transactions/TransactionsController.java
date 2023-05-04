package pl.mimuw.transactions;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mimuw.transactions.payload.BuyStockDto;
import pl.mimuw.transactions.payload.SellStockDto;

import static pl.mimuw.transactions.Utility.MESSAGE;
import static pl.mimuw.transactions.Utility.toResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionsController {
    private final TransactionsService transactionsService;

    @PostMapping("/buy")
    public ResponseEntity<?> buyStocks(@RequestBody BuyStockDto buyStockDto,
                                       @RequestHeader("Authorization") String token) {
        try {
            String message = transactionsService.buyStocks(buyStockDto, token);
            return ResponseEntity.ok(toResponse(MESSAGE, message));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(toResponse(MESSAGE, e.getMessage()));
        }
    }

    @PostMapping("/sell")
    public ResponseEntity<?> sellStocks(@RequestBody SellStockDto sellStockDto,
                                        @RequestHeader("Authorization") String token) {
        try {
            String message = transactionsService.sellStocks(sellStockDto, token);
            return ResponseEntity.ok(toResponse(MESSAGE, message));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(toResponse(MESSAGE, e.getMessage()));
        }
    }
}

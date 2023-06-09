package pl.mimuw.history;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static pl.mimuw.history.Utility.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/history")
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping
    public ResponseEntity<?> getHistoricalTransactions(@RequestHeader("Authorization") String token) {
        try {
            return ResponseEntity.ok(toResponse(HISTORY, historyService.getHistoricalTransactions(token)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(toResponse(MESSAGE, e.getMessage()));
        }
    }

}

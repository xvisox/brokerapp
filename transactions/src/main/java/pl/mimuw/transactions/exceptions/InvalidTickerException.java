package pl.mimuw.transactions.exceptions;

public class InvalidTickerException extends RuntimeException {
    public InvalidTickerException(String message) {
        super(message);
    }
}

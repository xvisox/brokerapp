package pl.mimuw.transactions.exceptions;

public class InvalidSharesAmountException extends RuntimeException {
    public InvalidSharesAmountException(String message) {
        super(message);
    }
}

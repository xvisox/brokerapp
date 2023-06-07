package pl.mimuw.transactions.exceptions;

public class InvalidBalanceException extends RuntimeException {
    public InvalidBalanceException(String message) { super(message); }
}

package com.arif.demo.exception;

public class InsufficientBalanceException extends RuntimeException {
    public static final String MESSAGE = "Insufficient balance";
    public InsufficientBalanceException() {
        super(MESSAGE);
    }
}

package com.arif.demo.exception.model;

import org.springframework.http.HttpStatus;

public class InsufficientBalanceException extends DemoBaseException {
    public static final String MESSAGE = "Insufficient balance";
    public InsufficientBalanceException() {
        super(MESSAGE, HttpStatus.BAD_REQUEST);
    }
}

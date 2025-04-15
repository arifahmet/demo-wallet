package com.arif.demo.exception.model;

import org.springframework.http.HttpStatus;

public class TransactionAllreadyCompletedException extends DemoBaseException {
    public TransactionAllreadyCompletedException() {
        super("Transaction already processed", HttpStatus.BAD_REQUEST);
    }
}

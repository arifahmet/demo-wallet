package com.arif.demo.exception.model;

import org.springframework.http.HttpStatus;

public class TransactionNotFoundException extends DemoBaseException {
    public TransactionNotFoundException() {
        super("Transaction Not Found.", HttpStatus.NOT_FOUND);
    }
}

package com.arif.demo.exception.model;

import org.springframework.http.HttpStatus;

public class WalletNotFoundException extends DemoBaseException {
    public WalletNotFoundException() {
        super("Wallet not found", HttpStatus.NOT_FOUND);
    }
}

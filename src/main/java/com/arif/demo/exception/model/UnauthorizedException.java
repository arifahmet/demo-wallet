package com.arif.demo.exception.model;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends DemoBaseException {
    public UnauthorizedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}

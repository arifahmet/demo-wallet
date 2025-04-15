package com.arif.demo.exception.model;

import org.springframework.http.HttpStatus;

public class TokenSerializationException extends DemoBaseException {
    public TokenSerializationException(String message, Throwable cause) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

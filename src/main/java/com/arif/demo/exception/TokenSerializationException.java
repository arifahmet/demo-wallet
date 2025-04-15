package com.arif.demo.exception;

public class TokenSerializationException extends RuntimeException{
    public TokenSerializationException(String message, Throwable cause) {
        super(message, cause);
    }
}

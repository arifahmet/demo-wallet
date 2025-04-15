package com.arif.demo.exception.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
@Setter
public class DemoBaseException extends RuntimeException {
    private final String message;
    private final HttpStatus httpStatus;

    public DemoBaseException(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }
}

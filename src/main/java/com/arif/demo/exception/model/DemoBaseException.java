package com.arif.demo.exception.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class DemoBaseException extends RuntimeException {
    private final String message;
    private final HttpStatus httpStatus;


}

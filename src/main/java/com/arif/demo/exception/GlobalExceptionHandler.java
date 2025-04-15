package com.arif.demo.exception;

import com.arif.demo.exception.model.DemoBaseException;
import com.arif.demo.exception.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({DemoBaseException.class})
    public ResponseEntity<ErrorResponse> handleDemoBaseException(DemoBaseException e) {
        log.info("DemoBaseException occurred", e);
        return ResponseEntity.status(e.getHttpStatus()).body(new ErrorResponse(List.of(e.getMessage())));
    }

    @ExceptionHandler({WebExchangeBindException.class})
    public ResponseEntity<ErrorResponse> handleWebExchangeBindException(WebExchangeBindException e) {
        log.info("WebExchangeBindException occurred", e);
        final List<String> errorMessageList = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = "";
            if (error instanceof FieldError fieldError) {
                fieldName = fieldError.getField();
            }
            String errorMessage = error.getDefaultMessage();
            errorMessageList.add(fieldName + " " + errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(errorMessageList));
    }
}

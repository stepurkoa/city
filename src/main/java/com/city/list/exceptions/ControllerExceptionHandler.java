package com.city.list.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<?> handleInvalidTopUpTypeException(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).build();
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<String> handleException(ServiceException ex) {
        return ResponseEntity.status(ex.getError().getStatus()).body(ex.getMessage());
    }
}

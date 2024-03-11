package com.demoProject.crp.CustomException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(value = { ToDoNotFoundException.class })
    public ResponseEntity<Object> handleApiRequestException(ToDoNotFoundException ex) {
        Exception exception = new Exception(ex.getMessage(), ex.http, ex.ErrorDetails);
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = { CustomerNotFoundException.class })
    public ResponseEntity<Object> handleApiRequestException(CustomerNotFoundException ex) {
        Exception exception = new Exception(ex.getMessage(), ex.http, ex.ErrorDetails);
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }
}

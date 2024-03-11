package com.demoProject.crp.CustomException;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ToDoNotFoundException extends RuntimeException{
    String ErrorDetails;
    HttpStatus http;

    public String getErrorDetails() {
        return ErrorDetails;
    }

    public ToDoNotFoundException(String msg, String errorDetails, HttpStatus http) {
        super(msg);
        this.ErrorDetails = errorDetails;
        this.http = http;
    }

    public ToDoNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        return new StackTraceElement[0];
    }

}


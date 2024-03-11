package com.demoProject.crp.CustomException;

import org.springframework.http.HttpStatus;

public class CustomerNotFoundException extends RuntimeException{
    String ErrorDetails;
    HttpStatus http;

    public String getErrorDetails() {
        return ErrorDetails;
    }

    public CustomerNotFoundException(String msg, String errorDetails, HttpStatus http) {
        super(msg);
        this.ErrorDetails = errorDetails;
        this.http = http;
    }
    public CustomerNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        return new StackTraceElement[0];
    }


}

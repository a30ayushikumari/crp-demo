package com.demoProject.crp.CustomException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exception {
    private String message;

    private HttpStatus httpStatus;
    private String ErrorDetails;

}

package com.demoProject.crp.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminDto {
    private HttpStatusCode statusCode;
    private String ErrorMessage;
    private String message;
    private String name;
    private String username;
    private String email;
}

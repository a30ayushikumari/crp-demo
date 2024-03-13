package com.demoProject.crp.Dto;

import com.demoProject.crp.Entity.Admin;
import com.demoProject.crp.Entity.Customer;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

public class RegistrationResponse {
    private Integer httpStatusCode;
    private String message;

    private Admin admin;
    private CustomerDto customer;

    private String errorMessage;
}

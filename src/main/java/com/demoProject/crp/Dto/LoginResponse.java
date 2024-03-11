package com.demoProject.crp.Dto;

import com.demoProject.crp.Entity.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//Does not show null in the response
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse {
    private Integer status;
    private String loginMessage;
    private String username;
    private String email;
    private Role authority;
    private String token;
    private String refreshToken;
    private String errorMessage;
    private String tokenExpirationTime;


}

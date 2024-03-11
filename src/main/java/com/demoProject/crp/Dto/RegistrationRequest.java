package com.demoProject.crp.Dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {
    private int id;
    @NotBlank(message = "Name Cannot be blank")
    @NotNull(message = "Name Cannot be Null")
    private String name;

    @Pattern(regexp = "^[a-zA-Z0-9_-]{3,16}$")
    private String username;

    @Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @Size(min = 5, max = 16, message = "Password should be between 5 to 16 characters")
    private String password;

}

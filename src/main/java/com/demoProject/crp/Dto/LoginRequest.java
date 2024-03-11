package com.demoProject.crp.Dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @Pattern(regexp = "^[a-zA-Z0-9_-]{3,16}$")
    private String username;

    @NotBlank(message = "Password Cannot be Blank")
    @Size(min = 5, max = 16, message = "Password should be between 5 to 16 characters")
    private String password;

}

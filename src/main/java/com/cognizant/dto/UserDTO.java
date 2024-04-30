package com.cognizant.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {

    @NotBlank(message = "Username is mandatory")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Username must only contain alphanumeric characters")
    private String username;

    @NotBlank(message = "Role is mandatory")
    @Pattern(regexp = "^(bankManager|customer)$", message = "Role must be either 'bankManager' or 'customer'")
    private String role;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

}


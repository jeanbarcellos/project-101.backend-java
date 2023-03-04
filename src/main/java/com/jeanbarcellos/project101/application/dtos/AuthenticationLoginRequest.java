package com.jeanbarcellos.project101.application.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class AuthenticationLoginRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}

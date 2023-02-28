package com.jeanbarcellos.project101.application.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class AuthenticationLoginRequest {

    @NotBlank(message = "O campo 'email' não deve ser nulo ou estar vazio")
    @Email(message = "O campo 'email' deve ser um endereço de e-mail válido")
    private String email;

    @NotBlank(message = "O campo 'password' não deve ser nulo ou estar vazio")
    private String password;
}

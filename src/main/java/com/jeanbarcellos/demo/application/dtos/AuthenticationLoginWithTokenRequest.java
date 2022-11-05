package com.jeanbarcellos.demo.application.dtos;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class AuthenticationLoginWithTokenRequest {

    @NotBlank(message = "O campo 'token' não deve ser nulo ou estar vazio")
    private String token;
}

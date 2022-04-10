package com.jeanbarcellos.demo.app.application.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AuthenticationLoginWithTokenRequest {

    @NotNull(message = "Campo não informado")
    @NotBlank(message = "Não deve estar em branco")
    private String token;
}

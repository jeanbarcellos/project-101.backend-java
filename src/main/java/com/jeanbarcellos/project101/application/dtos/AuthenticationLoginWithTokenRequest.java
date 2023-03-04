package com.jeanbarcellos.project101.application.dtos;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class AuthenticationLoginWithTokenRequest {

    @NotBlank
    private String token;
}

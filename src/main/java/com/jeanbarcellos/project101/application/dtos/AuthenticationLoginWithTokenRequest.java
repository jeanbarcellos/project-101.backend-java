package com.jeanbarcellos.project101.application.dtos;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Builder
@AllArgsConstructor
@Accessors(chain = true)
public class AuthenticationLoginWithTokenRequest {

    @NotBlank
    private String token;
}

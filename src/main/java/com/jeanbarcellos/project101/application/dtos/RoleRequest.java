package com.jeanbarcellos.project101.application.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class RoleRequest {

    public static final String ROLLE_NAME_PATTERN = "^[a-z0-9\\.\\-]*$";

    @NotBlank
    @Size(min = 4, max = 128)
    @Pattern(regexp = ROLLE_NAME_PATTERN, message = "está fora de padrão. Deve conter apenas letras minúsculaas, números, hiffen (-) ou ponto (.)")
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private List<UUID> childRoles = new ArrayList<>();
}

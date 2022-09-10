package com.jeanbarcellos.demo.application.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class RoleRequest {

    @NotNull(message = "Valor não informado")
    @NotBlank
    @Size(min = 4, max = 128, message = "O tamanho deve ser entre 4 e 128 caracteres")
    private String name;

    @NotNull(message = "Valor não informado")
    @NotBlank(message = "Não deve estar em branco")
    private String description;

    @NotNull(message = "Campo não informado")
    private List<UUID> childRoles = new ArrayList<>();
}

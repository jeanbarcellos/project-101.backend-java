package com.jeanbarcellos.project101.application.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class RoleRequest {

    @NotBlank
    @Size(min = 4, max = 128)
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private List<UUID> childRoles = new ArrayList<>();
}

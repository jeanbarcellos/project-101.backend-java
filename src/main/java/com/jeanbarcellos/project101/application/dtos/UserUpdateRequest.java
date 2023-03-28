package com.jeanbarcellos.project101.application.dtos;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jeanbarcellos.project101.domain.enums.UserStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserUpdateRequest {

    @JsonIgnore
    private UUID id;

    @NotBlank
    @Size(min = 4, max = 128)
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotNull
    private UserStatus status;

    @NotNull
    private List<String> roles;
}

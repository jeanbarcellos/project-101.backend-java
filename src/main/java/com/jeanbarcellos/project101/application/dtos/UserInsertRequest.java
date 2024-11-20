package com.jeanbarcellos.project101.application.dtos;

import java.util.List;

import com.jeanbarcellos.project101.domain.enums.UserStatus;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class UserInsertRequest {

    @NotBlank
    @Size(min = 4, max = 128)
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    @NotNull
    private UserStatus status;

    @NotNull
    private List<String> roles;
}

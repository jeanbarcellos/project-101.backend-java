package com.jeanbarcellos.demo.application.dtos;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.jeanbarcellos.demo.domain.enums.UserStatus;

import lombok.Data;

@Data
public class UserRequest {

    @NotBlank(message = "O campo 'name' não deve ser nulo ou estar vazio")
    @Size(min = 4, max = 128, message = "O campo 'name' deve possuir tamanho entre {min} e {max} caracteres")
    private String name;

    @NotBlank(message = "O campo 'email' não deve ser nulo ou estar vazio")
    @Email(message = "O campo 'email' deve ser um endereço de e-mail válido")
    private String email;

    @NotBlank(message = "O campo 'password' não deve ser nulo ou estar vazio")
    private String password;

    @NotNull(message = "O campo 'status' não deve ser nulo")
    private UserStatus status;

    @NotNull(message = "O campo 'roles' não deve ser nulo")
    private List<UUID> roles;
}

package com.jeanbarcellos.core.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ErrorResponse {

    @Schema(name = "message", description = "Mensagem")
    private String message;

    @Schema(name = "errors", description = "Lista de Erros")
    private Collection<String> errors = new ArrayList<>();

    public ErrorResponse(String message) {
        this.message = message;
    }

    public ErrorResponse(String message, Collection<String> errors) {
        this.message = message;
        this.errors = errors;
    }

    public ErrorResponse(String message, String... errors) {
        this.message = message;
        this.errors.addAll(Arrays.asList(errors));
    }

    public boolean hasErrors() {
        return !this.errors.isEmpty();
    }

    public static ErrorResponse of(String message) {
        return new ErrorResponse(message);
    }

    public static ErrorResponse of(String message, Collection<String> errors) {
        return new ErrorResponse(message, errors);
    }

}

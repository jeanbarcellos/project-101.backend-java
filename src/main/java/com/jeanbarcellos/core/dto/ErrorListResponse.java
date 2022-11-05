package com.jeanbarcellos.core.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.http.HttpStatus;

import com.jeanbarcellos.core.exception.ValidationException;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter

public class ErrorListResponse extends ErrorResponse {

    @Schema(name = "errors", description = "Lista de Erros")
    private Collection<String> errors = new ArrayList<>();

    public ErrorListResponse(String message) {
        super(message);
    }

    public ErrorListResponse(String message, Collection<String> errors) {
        super(message);
        this.errors = errors;
    }

    public ErrorListResponse(String message, String... errors) {
        super(message);
        this.errors.addAll(Arrays.asList(errors));
    }

    public ErrorListResponse(Integer status, String message) {
        super(status, message);
    }

    public ErrorListResponse(Integer status, String message, Collection<String> errors) {
        super(status, message);
        this.errors = errors;
    }

    public ErrorListResponse(Integer status, String message, String... errors) {
        super(status, message);
        this.errors.addAll(Arrays.asList(errors));
    }

    public boolean hasErrors() {
        return !this.errors.isEmpty();
    }

    public static ErrorListResponse of(ValidationException exception) {
        return new ErrorListResponse(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(),
                exception.getErrors());
    }
}

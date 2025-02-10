package com.jeanbarcellos.core.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * Response to Error with list of details
 *
 * @author Jean Silva de Barcellos (jeanbarcellos@hotmail.com)
 */
@Getter
public class ErrorResponse {

    @Schema(name = "message", description = "Mensagem")
    private final String message;

    @Schema(name = "errors", description = "Lista de Erros")
    @JsonInclude(Include.NON_NULL)
    private final List<String> errors;

    public ErrorResponse(String message) {
        this.message = message;
        this.errors = null;
    }

    public ErrorResponse(String message, List<String> errors) {
        this.message = message;
        this.errors = errors;
    }

    public boolean hasErrors() {
        return !this.errors.isEmpty();
    }

    public static ErrorResponse of(String message) {
        return new ErrorResponse(message);
    }

    public static ErrorResponse of(String message, List<String> errors) {
        return new ErrorResponse(message, errors);
    }

}

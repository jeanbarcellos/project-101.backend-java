package com.jeanbarcellos.core.dto;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ErrorResponse {

    @Schema(name = "status", description = "Status Code", defaultValue = "400")
    private Integer status = 400;

    @Schema(name = "message", description = "Mensagem")
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public static ErrorResponse badRequest(String message) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message);
    }

    public static ErrorResponse notFound(String message) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), message);
    }

    public static ErrorResponse unauthorized(String message) {
        return new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), message);
    }

    public static ErrorResponse forbidden(String message) {
        return new ErrorResponse(HttpStatus.FORBIDDEN.value(), message);
    }

}

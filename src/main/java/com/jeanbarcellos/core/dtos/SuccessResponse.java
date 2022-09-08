package com.jeanbarcellos.core.dtos;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuccessResponse {

    @Schema(name = "status", description = "Status Code", defaultValue = "400")
    @Builder.Default
    private Integer status = 200;

    @Schema(name = "message", description = "Mensagem")
    private String message;

    public static SuccessResponse create(String message) {
        return SuccessResponse
                .builder()
                .status(HttpStatus.OK.value())
                .message(message)
                .build();
    }
}
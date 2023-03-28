package com.jeanbarcellos.core.dto;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuccessResponse {

    @Schema(name = "status", description = "Status Code", defaultValue = "200")
    @Builder.Default
    private Integer status = 200;

    @Schema(name = "message", description = "Mensagem")
    private String message;

    public static SuccessResponse of(String message) {
        return SuccessResponse
                .builder()
                .status(HttpStatus.OK.value())
                .message(message)
                .build();
    }
}
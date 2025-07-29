package com.jeanbarcellos.project101.presentation.web.controllers;

import static com.jeanbarcellos.core.constants.ApiConstants.BEARER_KEY;
import static com.jeanbarcellos.project101.infra.configurations.Roles.HAS_ROLE_ROOT;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeanbarcellos.core.web.ControllerBase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Tag(name = "Utilitários", description = "Utilitários da API")
@RestController
@RequestMapping("/utils")
@PreAuthorize(HAS_ROLE_ROOT)
@SecurityRequirement(name = BEARER_KEY)
@RequiredArgsConstructor
public class UtilsController extends ControllerBase {

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/guid-generate")
    @Operation(summary = "Gerar GUID/UUID", description = "Gera um token GUID")
    public ResponseEntity<UtilsGenerateGuidResponse> generateGuid() {

        var response = UtilsGenerateGuidResponse.of(UUID.randomUUID().toString());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/password-encode")
    @Operation(summary = "Codificar uma senha", description = "Realiza a codificação de uma senha informada")
    public ResponseEntity<UtilsPasswordEncodeResponse> passwordEncode(@RequestBody UtilsPasswordEncodeRequest request) {

        var response = UtilsPasswordEncodeResponse.of(passwordEncoder.encode(request.getPassword()));

        return ResponseEntity.ok(response);
    }

    @Data
    @AllArgsConstructor(staticName = "of")
    public static class UtilsGenerateGuidResponse {
        private String guid;
    }

    @Data
    public static class UtilsPasswordEncodeRequest {
        private String password;
    }

    @Data
    @AllArgsConstructor(staticName = "of")
    public static class UtilsPasswordEncodeResponse {
        private String passwordEncoded;
    }

}

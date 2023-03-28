package com.jeanbarcellos.project101.presentation.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeanbarcellos.core.web.ControllerBase;
import com.jeanbarcellos.project101.application.dtos.AuthenticationLoginRequest;
import com.jeanbarcellos.project101.application.dtos.AuthenticationLoginResponse;
import com.jeanbarcellos.project101.application.dtos.AuthenticationLoginWithTokenRequest;
import com.jeanbarcellos.project101.application.services.AuthenticationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Autenticação da API")
public class AuthenticationController extends ControllerBase {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    @Operation(summary = "Logar com email", description = "Realiza o login com email e obtém o token de acesso")
    public ResponseEntity<AuthenticationLoginResponse> login(@RequestBody AuthenticationLoginRequest request) {
        return ResponseEntity.ok(this.authenticationService.login(request));
    }

    @PostMapping("/login-with-token")
    @Operation(summary = "Logar com token", description = "Realiza o login com token e obtém o token de acesso")
    public ResponseEntity<AuthenticationLoginResponse> loginWithToken(
            @RequestBody AuthenticationLoginWithTokenRequest request) {
        return ResponseEntity.ok(authenticationService.loginWithToken(request));
    }

}

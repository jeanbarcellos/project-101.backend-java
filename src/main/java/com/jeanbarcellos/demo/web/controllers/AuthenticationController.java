package com.jeanbarcellos.demo.web.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeanbarcellos.core.web.ControllerBase;
import com.jeanbarcellos.demo.application.dtos.AuthenticationLoginRequest;
import com.jeanbarcellos.demo.application.dtos.AuthenticationLoginResponse;
import com.jeanbarcellos.demo.application.dtos.AuthenticationLoginWithTokenRequest;
import com.jeanbarcellos.demo.application.services.AuthenticationService;

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
    public ResponseEntity<AuthenticationLoginResponse> login(@RequestBody @Valid AuthenticationLoginRequest request) {
        var response = authenticationService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/loginWithToken")
    @Operation(summary = "LOgar com token", description = "Realiza o login com token e obtém o token de acesso")
    public ResponseEntity<AuthenticationLoginResponse> loginWithToken(
            @RequestBody @Valid AuthenticationLoginWithTokenRequest request) {
        var response = authenticationService.loginWithToken(request);
        return ResponseEntity.ok(response);
    }

}

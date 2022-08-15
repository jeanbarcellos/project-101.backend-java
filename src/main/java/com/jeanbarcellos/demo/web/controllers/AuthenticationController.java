package com.jeanbarcellos.demo.web.controllers;

import javax.validation.Valid;

import com.jeanbarcellos.core.web.ControllerBase;
import com.jeanbarcellos.demo.application.dtos.AuthenticationLoginRequest;
import com.jeanbarcellos.demo.application.dtos.AuthenticationLoginResponse;
import com.jeanbarcellos.demo.application.dtos.AuthenticationLoginWithTokenRequest;
import com.jeanbarcellos.demo.application.services.AuthenticationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController extends ControllerBase {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationLoginResponse> login(@RequestBody @Valid AuthenticationLoginRequest request) {
        var response = authenticationService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/loginWithToken")
    public ResponseEntity<AuthenticationLoginResponse> loginWithToken(
            @RequestBody @Valid AuthenticationLoginWithTokenRequest request) {
        var response = authenticationService.loginWithToken(request);
        return ResponseEntity.ok(response);
    }

}

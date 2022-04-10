package com.jeanbarcellos.demo.app.web.controllers;

import javax.validation.Valid;

import com.jeanbarcellos.demo.app.application.dtos.AuthenticationLoginRequest;
import com.jeanbarcellos.demo.app.application.dtos.AuthenticationLoginResponse;
import com.jeanbarcellos.demo.app.application.dtos.AuthenticationLoginWithTokenRequest;
import com.jeanbarcellos.demo.app.application.services.AuthenticationService;
import com.jeanbarcellos.demo.core.web.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController extends Controller {

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

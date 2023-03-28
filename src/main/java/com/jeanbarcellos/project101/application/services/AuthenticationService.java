package com.jeanbarcellos.project101.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.jeanbarcellos.core.exception.AuthenticationException;
import com.jeanbarcellos.core.validation.Validator;
import com.jeanbarcellos.project101.application.dtos.AuthenticationLoginRequest;
import com.jeanbarcellos.project101.application.dtos.AuthenticationLoginResponse;
import com.jeanbarcellos.project101.application.dtos.AuthenticationLoginWithTokenRequest;
import com.jeanbarcellos.project101.domain.entities.User;
import com.jeanbarcellos.project101.infra.configurations.SecurityAuthenticationService;

@Service
public class AuthenticationService {

    private static final String MSG_ERROR_INVALID_TOKEN = "Token de autenticação inválido";

    @Autowired
    private Validator validator;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private SecurityAuthenticationService repository;

    public AuthenticationLoginResponse login(AuthenticationLoginRequest request) {
        this.validator.validate(request);

        var credentials = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

        var authentication = this.authenticationManager.authenticate(credentials);

        var user = (User) authentication.getPrincipal();

        var token = this.jwtService.generateToken(user);

        return AuthenticationLoginResponse.of(user, token);
    }

    public AuthenticationLoginResponse loginWithToken(AuthenticationLoginWithTokenRequest request) {
        this.validator.validate(request);

        if (!this.jwtService.isValidToken(request.getToken())) {
            throw new AuthenticationException(MSG_ERROR_INVALID_TOKEN);
        }

        var username = jwtService.getTokenUsername(request.getToken());

        var user = repository.loadUserByUsername(username);

        var credentials = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(credentials);

        return AuthenticationLoginResponse.of((User) user, request.getToken());
    }

}

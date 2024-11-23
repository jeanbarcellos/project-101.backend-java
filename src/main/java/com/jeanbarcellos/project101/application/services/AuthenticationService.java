package com.jeanbarcellos.project101.application.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.jeanbarcellos.core.validation.Validator;
import com.jeanbarcellos.project101.application.dtos.AuthenticationLoginRequest;
import com.jeanbarcellos.project101.application.dtos.AuthenticationLoginResponse;
import com.jeanbarcellos.project101.application.dtos.AuthenticationLoginWithTokenRequest;
import com.jeanbarcellos.project101.domain.entities.User;

@Service
public class AuthenticationService {

    private final Validator validator;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    public AuthenticationService(Validator validator, AuthenticationManager authenticationManager,
            JwtService jwtService, UserDetailsService userDetailsService) {
        this.validator = validator;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

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

        this.jwtService.validateToken(request.getToken());

        var username = this.jwtService.getTokenUsername(request.getToken());

        var user = this.userDetailsService.loadUserByUsername(username);

        var credentials = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(credentials);

        return AuthenticationLoginResponse.of((User) user, request.getToken());
    }

}

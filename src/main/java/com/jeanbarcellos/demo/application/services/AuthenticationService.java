package com.jeanbarcellos.demo.application.services;

import com.jeanbarcellos.demo.application.dtos.AuthenticationLoginRequest;
import com.jeanbarcellos.demo.application.dtos.AuthenticationLoginResponse;
import com.jeanbarcellos.demo.application.dtos.AuthenticationLoginWithTokenRequest;
import com.jeanbarcellos.demo.config.SecurityAuthenticationService;
import com.jeanbarcellos.demo.core.exceptions.AuthenticationException;
import com.jeanbarcellos.demo.domain.entities.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private SecurityAuthenticationService repository;

    public AuthenticationLoginResponse login(AuthenticationLoginRequest request) {
        UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword());

        Authentication authentication = authenticationManager.authenticate(credentials);

        User user = (User) authentication.getPrincipal();

        String token = jwtService.generateToken(user);

        return AuthenticationLoginResponse.from(user, token);
    }

    public AuthenticationLoginResponse loginWithToken(AuthenticationLoginWithTokenRequest request) {

        if (!jwtService.isValidToken(request.getToken())) {
            throw new AuthenticationException("Token de autenticação inválido");
        }

        String username = jwtService.getTokenUsername(request.getToken());

        UserDetails user = repository.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                user, null, user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(credentials);

        return AuthenticationLoginResponse.from((User) user, request.getToken());
    }

}

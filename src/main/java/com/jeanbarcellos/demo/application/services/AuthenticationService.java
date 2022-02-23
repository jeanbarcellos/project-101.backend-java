package com.jeanbarcellos.demo.application.services;

import com.jeanbarcellos.demo.application.dtos.AuthenticationLoginRequest;
import com.jeanbarcellos.demo.application.dtos.AuthenticationLoginResponse;
import com.jeanbarcellos.demo.domain.entities.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public AuthenticationLoginResponse login(AuthenticationLoginRequest request) {
        UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword());

        Authentication authentication = authenticationManager.authenticate(credentials);

        User user = (User) authentication.getPrincipal();

        String token = jwtService.generateToken(user);

        return AuthenticationLoginResponse.from(user, token);
    }

}

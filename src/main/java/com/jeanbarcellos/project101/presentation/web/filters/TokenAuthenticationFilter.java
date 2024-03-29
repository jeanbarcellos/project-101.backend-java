package com.jeanbarcellos.project101.presentation.web.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jeanbarcellos.core.exception.AuthenticationException;
import com.jeanbarcellos.project101.application.services.JwtService;
import com.jeanbarcellos.project101.infra.configurations.SecurityAuthenticationService;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private static final String HEADER_AUTHORIZATION = "authorization";
    private static final String SCHEME_BEARER = "Bearer";
    private static final String EMPTY_SPACE = " ";
    private static final String REGEX_POINT = "\\.";

    private JwtService jwtService;

    private SecurityAuthenticationService repository;

    public TokenAuthenticationFilter(JwtService jwtService, SecurityAuthenticationService repository) {
        this.jwtService = jwtService;
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (checkForAuthentication(request)) {
            String tokenFromHeader = getTokenFromHeader(request);

            jwtService.validateToken(tokenFromHeader);

            this.authenticate(tokenFromHeader);
        }

        filterChain.doFilter(request, response);
    }

    private void authenticate(String token) {
        String username = jwtService.getTokenUsername(token);

        UserDetails user = repository.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                user, null, user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }

    private boolean checkForAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_AUTHORIZATION);
        return !(token == null || token.isEmpty());
    }

    private String getTokenFromHeader(HttpServletRequest request) {
        String authHeader = request.getHeader(HEADER_AUTHORIZATION);

        if (authHeader == null || authHeader.isEmpty()) {
            throw new AuthenticationException("Token não informado");
        }

        String[] parts = authHeader.split(EMPTY_SPACE);

        if (parts.length != 2) {
            throw new AuthenticationException("Token mal formatado.");
        }

        if (!parts[0].equals(SCHEME_BEARER)) {
            throw new AuthenticationException("Schema do token inválido.");
        }

        String token = parts[1];

        String[] tokenParts = token.split(REGEX_POINT);

        if (tokenParts.length != 3) {
            throw new AuthenticationException("Token inválido.");
        }

        return token;
    }

}

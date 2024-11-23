package com.jeanbarcellos.project101.presentation.web.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.jeanbarcellos.core.exception.JWTAuthenticationException;
import com.jeanbarcellos.project101.application.services.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private static final String HEADER_AUTHORIZATION = "authorization";
    private static final String SCHEME_BEARER = "Bearer";
    private static final String EMPTY_SPACE = " ";
    private static final String REGEX_POINT = "\\.";

    private final JwtService jwtService;

    private final UserDetailsService repository;

    private final HandlerExceptionResolver resolver;

    public TokenAuthenticationFilter(
            JwtService jwtService,
            UserDetailsService repository,
            @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.jwtService = jwtService;
        this.repository = repository;
        this.resolver = resolver;
    }

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        try {
            if (checkForAuthentication(request)) {
                String tokenFromHeader = getTokenFromHeader(request);

                jwtService.validateToken(tokenFromHeader);

                this.authenticate(tokenFromHeader);
            }

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            log.error("TokenAuthenticationFilter");
            resolver.resolveException(request, response, null, e);
        }
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
            throw new JWTAuthenticationException("Token não informado");
        }

        String[] parts = authHeader.split(EMPTY_SPACE);

        if (parts.length != 2) {
            throw new JWTAuthenticationException("Token mal formatado.");
        }

        if (!parts[0].equals(SCHEME_BEARER)) {
            throw new JWTAuthenticationException("Schema do token inválido.");
        }

        String token = parts[1];

        String[] tokenParts = token.split(REGEX_POINT);

        if (tokenParts.length != 3) {
            throw new JWTAuthenticationException("Token inválido.");
        }

        return token;
    }

}

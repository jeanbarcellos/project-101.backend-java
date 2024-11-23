package com.jeanbarcellos.project101.presentation.web.exceptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jeanbarcellos.core.dto.ErrorResponse;
import com.jeanbarcellos.core.exception.JWTAuthenticationException;

import lombok.extern.log4j.Log4j2;

/**
 * Manipula as exceções de segurança
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Log4j2
public class SecutiryHandler {

    // Autentication JEAN
    @ExceptionHandler(JWTAuthenticationException.class)
    public ResponseEntity<ErrorResponse> handle(JWTAuthenticationException exception) {
        return createResponseUnauthorized(exception.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handle(AuthenticationException exception) {
        return createResponseUnauthorized(String.format("Erro de autenticação: %s", getMessage(exception)));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handle(AccessDeniedException exception) {
        return createResponseForbidden("Acesso não autorizado.");
    }

    // 401
    private static ResponseEntity<ErrorResponse> createResponseUnauthorized(String message) {
        return new ResponseEntity<>(ErrorResponse.unauthorized(message), HttpStatus.UNAUTHORIZED);
    }

    // 403
    private static ResponseEntity<ErrorResponse> createResponseForbidden(String message) {
        return new ResponseEntity<>(ErrorResponse.forbidden(message), HttpStatus.FORBIDDEN);
    }

    private static String getMessage(AuthenticationException exception) {
        var msg = "";
        var clazz = exception.getClass();

        if (clazz == LockedException.class) {
            msg = "A conta do usuário está bloqueada.";
        } else if (clazz == DisabledException.class) {
            msg = "O usuário está desabilitado.";
        } else if (clazz == AccountStatusException.class) {
            msg = "Problemas no status da conta";
        } else if (clazz == UsernameNotFoundException.class) {
            msg = "Usuário não encontrado.";
        } else if (clazz == BadCredentialsException.class) {
            msg = "Credenciais iváliadas.";
        } else if (clazz == AuthenticationCredentialsNotFoundException.class) {
            msg = "Credenciais não encontradas.";
        } else if (clazz == InsufficientAuthenticationException.class) {
            msg = "Credenciais insuficientes";
        } else {
            msg = "";
        }

        return msg;
    }

}

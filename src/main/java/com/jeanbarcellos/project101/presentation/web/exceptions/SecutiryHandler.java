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
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jeanbarcellos.core.dto.ErrorResponse;
import com.jeanbarcellos.core.exception.AuthenticationException;
import com.jeanbarcellos.core.exception.AuthorizationException;

import lombok.extern.log4j.Log4j2;

/**
 * Manipula as exceções de segurança
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Log4j2
public class SecutiryHandler {

    // #region Autenticação

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handle(AuthenticationException exception) {
        return createResponseUnauthorized(exception.getMessage());
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ErrorResponse> handle(LockedException exception) {
        return createResponseUnauthorized("A conta do usuário está bloqueada.");
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ErrorResponse> handle(DisabledException exception) {
        return createResponseUnauthorized("O usuário está desabilitado.");
    }

    @ExceptionHandler(AccountStatusException.class)
    public ResponseEntity<ErrorResponse> handle(AccountStatusException exception) {
        return createResponseUnauthorized(
                "Erro ao tentar autenticar com o usuário. Entre em contato com o administrador do sistema");
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(UsernameNotFoundException exception) {
        return createResponseUnauthorized("Usuário não encontrado.");
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handle(BadCredentialsException exception) {
        return createResponseUnauthorized("Credenciais iváliadas.");
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(AuthenticationCredentialsNotFoundException exception) {
        return createResponseUnauthorized("Credenciais não encontradas.");
    }

    @ExceptionHandler(org.springframework.security.core.AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handle(
            org.springframework.security.core.AuthenticationException exception) {
        log.error(exception.getMessage());
        return createResponseUnauthorized("Erro de autenticação.");
    }

    // #endregion

    // #region Autorização

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<ErrorResponse> handle(AuthorizationException exception) {
        return createResponseForbidden(exception.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handle(AccessDeniedException exception) {
        return createResponseUnauthorized("Acesso não autorizado.");
    }

    // #endregion

    private static ResponseEntity<ErrorResponse> createResponseUnauthorized(String message) {
        return new ResponseEntity<>(ErrorResponse.unauthorized(message), HttpStatus.UNAUTHORIZED);
    }

    private static ResponseEntity<ErrorResponse> createResponseForbidden(String message) {
        return new ResponseEntity<>(ErrorResponse.forbidden(message), HttpStatus.FORBIDDEN);
    }

}

package com.jeanbarcellos.demo.config.exception;

import com.jeanbarcellos.demo.core.dtos.ErrorResponse;
import com.jeanbarcellos.demo.core.exceptions.AuthenticationException;
import com.jeanbarcellos.demo.core.exceptions.AuthorizationException;

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
    public ResponseEntity<?> handleException(AuthenticationException exception) {
        return createResponseUnauthorized(exception.getMessage());
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<?> handleException(LockedException exception) {
        return createResponseUnauthorized("A conta do usuário está bloqueada.");
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<?> handleException(DisabledException exception) {
        return createResponseUnauthorized("O usuário está desabilitado.");
    }

    @ExceptionHandler(AccountStatusException.class)
    public ResponseEntity<?> handleException(AccountStatusException exception) {
        return createResponseUnauthorized(
                "Erro ao tentar autenticar com o usuário. Entre em contato com o administrador do sistema");
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleException(UsernameNotFoundException exception) {
        return createResponseUnauthorized("Usuário não encontrado.");
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleException(BadCredentialsException exception) {
        return createResponseUnauthorized("Credenciais iváliadas.");
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<?> handleException(AuthenticationCredentialsNotFoundException exception) {
        return createResponseUnauthorized("Credenciais não encontradas.");
    }

    @ExceptionHandler(org.springframework.security.core.AuthenticationException.class)
    public ResponseEntity<?> handleException(org.springframework.security.core.AuthenticationException exception) {
        log.error(exception.getMessage());
        return createResponseUnauthorized("Erro de autenticação.");
    }

    // #endregion

    // #region Autorização

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<?> handleException(AuthorizationException exception) {
        return createResponseForbidden(exception.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleException(AccessDeniedException exception) {
        return createResponseUnauthorized("Acesso não autorizado.");
    }

    // #endregion

    private static ResponseEntity<?> createResponseUnauthorized(String message) {
        return new ResponseEntity<>(ErrorResponse.unauthorized(message), HttpStatus.UNAUTHORIZED);
    }

    private static ResponseEntity<?> createResponseForbidden(String message) {
        return new ResponseEntity<>(ErrorResponse.forbidden(message), HttpStatus.FORBIDDEN);
    }

}

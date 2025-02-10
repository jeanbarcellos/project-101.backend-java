package com.jeanbarcellos.core.exception;

public class JwtAuthenticationException extends ApplicationException {

    public JwtAuthenticationException(String message) {
        super(message);
    }

    public JwtAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

}

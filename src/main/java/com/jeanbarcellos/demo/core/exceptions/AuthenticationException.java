package com.jeanbarcellos.demo.core.exceptions;

public class AuthenticationException extends RuntimeException {

    public AuthenticationException(String message) {
        super(message);
    }
}

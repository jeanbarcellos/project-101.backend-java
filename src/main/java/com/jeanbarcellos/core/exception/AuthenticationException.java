package com.jeanbarcellos.core.exception;

/**
 * Authentication Exception
 *
 * @author Jean Silva de Barcellos (jeanbarcellos@hotmail.com)
 */
public class AuthenticationException extends ApplicationException {

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

}

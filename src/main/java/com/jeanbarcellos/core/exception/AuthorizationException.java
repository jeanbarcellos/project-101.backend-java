package com.jeanbarcellos.core.exception;

/**
 * Authorization Exception
 *
 * @author Jean Silva de Barcellos (jeanbarcellos@hotmail.com)
 */
public class AuthorizationException extends ApplicationException {

    public AuthorizationException(String message) {
            super(message);
        }

    public AuthorizationException(String message, Throwable cause) {
            super(message, cause);
        }

}

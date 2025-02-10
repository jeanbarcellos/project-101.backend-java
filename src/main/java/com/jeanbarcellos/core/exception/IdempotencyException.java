package com.jeanbarcellos.core.exception;

/**
 * Idempotency Exception
 *
 * @author Jean Silva de Barcellos (jeanbarcellos@hotmail.com)
 */
public class IdempotencyException extends ApplicationException {

    public IdempotencyException(String message) {
        super(message);
    }

    public IdempotencyException(String message, Throwable cause) {
        super(message, cause);
    }

}

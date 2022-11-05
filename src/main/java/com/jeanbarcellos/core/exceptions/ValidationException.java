package com.jeanbarcellos.core.exceptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Validação simples
 */
public class ValidationException extends RuntimeException {

    private final Collection<String> errors;

    public ValidationException(String message) {
        super(message);
        this.errors = new ArrayList<>();
    }

    public ValidationException(String message, Collection<String> errors) {
        super(message);
        this.errors = errors;
    }

    public ValidationException(String message, String... errors) {
        super(message);
        this.errors = Arrays.asList(errors);
    }

    public Collection<String> getErrors() {
        return this.errors;
    }

    public boolean hasErrors() {
        return !this.errors.isEmpty();
    }

    public static ValidationException of(String message) {
        return new ValidationException(message);
    }

    public static ValidationException of(String message, Collection<String> errors) {
        return new ValidationException(message, errors);
    }

}
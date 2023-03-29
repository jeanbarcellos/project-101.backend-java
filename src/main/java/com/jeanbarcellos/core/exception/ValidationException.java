package com.jeanbarcellos.core.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

/**
 * Validação simples
 */
public class ValidationException extends ApplicationException {

    public static final String ERRORS_PREFIX = "Erros=";

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

    public String getMessageToLog() {
        var mensagemLog = this.getMessage();

        if (this.hasErrors()) {
            mensagemLog += StringUtils.LF + ERRORS_PREFIX + this.getErrors().toString();
        }

        return mensagemLog;
    }

    public static ValidationException of(String message) {
        return new ValidationException(message);
    }

    public static ValidationException of(String message, Collection<String> errors) {
        return new ValidationException(message, errors);
    }

}
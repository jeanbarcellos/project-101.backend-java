package com.jeanbarcellos.core.validation;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;

import org.springframework.stereotype.Component;

import com.jeanbarcellos.core.exception.ValidationException;
import com.jeanbarcellos.project101.infra.configurations.constants.MessageConstants;

@Component
public class Validator {

    public static final String MSG_ERROR_DEFAULT = "O campo '%s' %s";

    public <T> Set<ConstraintViolation<T>> check(T model) {
        return this.getInnerValidator().validate(model);
    }

    public <T> void validate(T model) {
        var constraintViolations = this.check(model);

        if (!constraintViolations.isEmpty()) {
            throw createValidateException(constraintViolations);
        }
    }

    public static <T> ValidationException createValidateException(Set<ConstraintViolation<T>> constraintViolations) {
        return ValidationException.of(MessageConstants.ERROR_VALIDATION, createMessages(constraintViolations));
    }

    public static <T> List<String> createMessages(Set<ConstraintViolation<T>> constraintViolations) {
        return constraintViolations.stream()
                .map(Validator::getMessage)
                .collect(Collectors.toList());
    }

    private static <T> String getMessage(ConstraintViolation<T> constraintViolation) {
        return String.format(MSG_ERROR_DEFAULT,
                constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
    }

    private javax.validation.Validator getInnerValidator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }
}

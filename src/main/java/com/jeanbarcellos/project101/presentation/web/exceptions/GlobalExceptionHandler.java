package com.jeanbarcellos.project101.presentation.web.exceptions;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.jeanbarcellos.core.dto.ErrorResponse;
import com.jeanbarcellos.core.exception.NotFoundException;
import com.jeanbarcellos.core.exception.ValidationException;
import com.jeanbarcellos.project101.infra.constants.MessageConstants;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
@Slf4j
public class GlobalExceptionHandler {

    public static final String MSG_VALIDATION_ERROR_DEFAULT = "O campo '%s' %s";

    // App ----------------

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(NotFoundException exception) {
        log.error(exception.getMessage(), exception);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND.value())
                .body(new ErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handle(ValidationException exception) {
        log.error(exception.getMessage(), exception);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST.value())
                .body(new ErrorResponse(exception.getMessage(), exception.getErrors()));
    }

    // Spring Web ----------------

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException exception) {
        log.error(exception.getMessage(), exception);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST.value())
                .body(new ErrorResponse(MessageConstants.ERROR_VALIDATION, generateMessages(exception)));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handle(NoResourceFoundException exception) {
        log.error(exception.getMessage(), exception);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND.value())
                .body(new ErrorResponse(MessageConstants.ERROR_VALIDATION));
    }

    // Todo resto ---------------------------------------------------

    // Todas as demais exceptions
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> handle(Throwable exception) {
        log.error(exception.getMessage(), exception);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .body(new ErrorResponse(MessageConstants.ERROR_SERVICE));
    }

    private static Collection<String> generateMessages(MethodArgumentNotValidException exception) {
        return exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> generateMessage(error))
                .collect(Collectors.toList());
    }

    private static String generateMessage(FieldError error) {
        return String.format(MSG_VALIDATION_ERROR_DEFAULT, error.getField(), error.getDefaultMessage());
    }

}

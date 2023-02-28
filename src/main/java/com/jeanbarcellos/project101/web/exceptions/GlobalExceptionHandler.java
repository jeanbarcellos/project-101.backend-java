package com.jeanbarcellos.project101.web.exceptions;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jeanbarcellos.core.dto.ErrorListResponse;
import com.jeanbarcellos.core.dto.ErrorResponse;
import com.jeanbarcellos.core.exception.NotFoundException;
import com.jeanbarcellos.core.exception.ValidationException;
import com.jeanbarcellos.project101.config.constants.MessageConstants;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(NotFoundException exception) {
        log.error(exception.getMessage(), exception);

        var response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage());

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handle(ValidationException exception) {
        log.error(exception.getMessage(), exception);

        var response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage());

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException exception) {
        log.error(exception.getMessage(), exception);

        var response = new ErrorListResponse(
                HttpStatus.BAD_REQUEST.value(),
                MessageConstants.ERROR_VALIDATION, generateMessages(exception));

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // Todas as demais exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handle(Exception exception) {
        log.error(exception.getMessage(), exception);

        var response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), MessageConstants.ERROR_SERVICE);

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    private static Collection<String> generateMessages(MethodArgumentNotValidException exception) {
        Collection<String> errors = new ArrayList<>();
        exception.getBindingResult().getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
        return errors;
    }

}
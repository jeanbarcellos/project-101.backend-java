package com.jeanbarcellos.demo.config.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jeanbarcellos.core.dtos.ErrorResponse;
import com.jeanbarcellos.core.exceptions.NotFoundException;
import com.jeanbarcellos.core.exceptions.ValidationException;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class GlobalExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException exception) {
        logger.error(exception.getMessage());

        var response = new ErrorResponse();
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMessage(exception.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(ValidationException exception) {
        var response = new ErrorResponse();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage(exception.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Todas as demais exceptions
    // @ExceptionHandler(Exception.class)
    // public ResponseEntity<?> handleException(Exception exception) {

    // logger.error(exception.getMessage());

    // var response = new ErrorResponse();
    // response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    // response.setMessage("Erro Interno do Servidor. Tente novamente mais tarde.");

    // return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    // }

}

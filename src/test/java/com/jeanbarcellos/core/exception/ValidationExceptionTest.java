package com.jeanbarcellos.core.exception;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ValidationExceptionTest {

    String message = "Mensagem de erro";
    List<String> errors = Arrays.asList("Erro 1", "Erro 1", "Erro2");
    RuntimeException cause = new RuntimeException("Causa Raiz");

    @Test
    void constructor_entryMessage_shouldReturnEmptyObject() {

        // Arrange && Act
        var exception = new ValidationException(message);

        // Assert
        assertInstanceOf(ApplicationException.class, exception);
        assertEquals(message, exception.getMessage());
        assertNull(exception.getErrors());
        assertFalse(exception.hasErrors());
        assertNull(exception.getCause());
    }

    @Test
    void constructor_entryMessageAndCause_shouldReturnEmptyObject() {

        // Arrange && Act
        var exception = new ValidationException(message, cause);

        // Assert
        assertInstanceOf(ApplicationException.class, exception);
        assertEquals(message, exception.getMessage());
        assertFalse(exception.hasErrors());
        assertFalse(exception.hasErrors());
        assertNotNull(exception.getCause());
    }

    @Test
    void constructor_entryMessageAndListErrors_shouldReturnObject() {

        // Arrange && Act
        var exception = new ValidationException(message, errors);

        // Assert
        assertEquals(message, exception.getMessage());
        assertTrue(exception.hasErrors());
        assertEquals(errors, exception.getErrors());
        assertEquals(3, exception.getErrors().size());
        assertNull(exception.getCause());
    }

    @Test
    void constructor_entryMessageAndStringErrors_shouldReturnObject() {

        // Arrange && Act
        var exception = new ValidationException(message, errors.get(0), errors.get(1), errors.get(2));

        // Assert
        assertEquals(message, exception.getMessage());
        assertTrue(exception.hasErrors());
        assertTrue(exception.getErrors().contains(errors.get(0)));
        assertTrue(exception.getErrors().contains(errors.get(1)));
        assertTrue(exception.getErrors().contains(errors.get(2)));
        assertEquals(3, exception.getErrors().size());
        assertNull(exception.getCause());
    }

    @Test
    void constructor_entryMessageAndListErrorsAndCause_shouldReturnEmptyObject() {

        // Arrange && Act
        var exception = new ValidationException(message, errors, cause);

        // Assert
        assertInstanceOf(ApplicationException.class, exception);
        assertEquals(message, exception.getMessage());
        assertEquals(3, exception.getErrors().size());
        assertTrue(exception.hasErrors());
        assertEquals(errors, exception.getErrors());
        assertNotNull(exception.getCause());
    }

    @Test
    void getMessageToLog_entryMessageAndListErrors_shouldReturnMessageWithoutErrors() {

        // Arrange
        var exception = new ValidationException(message);

        // Act
        var result = exception.getMessageToLog();

        // Assert
        assertEquals(message, result);
    }

    @Test
    void getMessageToLog_entryMessageAndListErrors_shouldReturnMessageWithErrors() {

        // Arrange
        var exception = new ValidationException(message, errors);

        // Act
        var result = exception.getMessageToLog();

        // Assert
        assertThat(result, CoreMatchers.containsString(ValidationException.ERRORS_PREFIX));
    }

    @Test
    void of_entryMessage_shouldReturnEmptyObject() {

        // Arrange && Act
        var exception = ValidationException.of(message);

        // Assert
        assertInstanceOf(ApplicationException.class, exception);
        assertEquals(message, exception.getMessage());
        assertFalse(exception.hasErrors());
    }

    @Test
    void of_entryMessageAndListErrors_shouldReturnObject() {

        // Arrange && Act
        var exception = ValidationException.of(message, errors);

        // Assert
        assertEquals(message, exception.getMessage());
        assertTrue(exception.hasErrors());
        assertEquals(errors, exception.getErrors());
    }

}

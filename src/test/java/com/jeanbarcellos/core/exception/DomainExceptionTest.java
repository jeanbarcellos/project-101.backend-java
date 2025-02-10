package com.jeanbarcellos.core.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DomainExceptionTest {

    private String message = "Mensagem de erro";
    private Throwable cause = new RuntimeException("Erro forçado");

    @Test
    void constructor_entryMessage_shouldReturnObject() {

        // Arrange && Act
        var exception = new DomainException(message);

        // Assert
        assertEquals(message, exception.getMessage());
    }

    @Test
    void constructor_entryMessageAndCause_shouldReturnbject() {

        // Arrange && Act
        var exception = new DomainException(message, cause);

        // Assert
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

}

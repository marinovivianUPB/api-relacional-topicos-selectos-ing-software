package com.topicossoftware.marino.upb.edu.api.app.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioServiceExceptionTest {

    @Test
    void testConstructorSetsMessageAndCode() {
        String message = "User not found";
        String errorCode = "404";

        UsuarioServiceException exception = new UsuarioServiceException(message, errorCode);

        assertEquals(message, exception.getMessage());
        assertEquals(errorCode, exception.getErrorCode());
    }

    @Test
    void testIsRuntimeException() {
        UsuarioServiceException exception = new UsuarioServiceException("Test", "500");
        assertTrue(exception instanceof RuntimeException);
    }
}
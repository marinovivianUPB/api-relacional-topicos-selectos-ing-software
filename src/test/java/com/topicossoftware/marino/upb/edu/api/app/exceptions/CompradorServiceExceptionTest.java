package com.topicossoftware.marino.upb.edu.api.app.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CompradorServiceExceptionTest {

    @Test
    void testConstructorSetsMessageAndCode() {
        String message = "Buyer not found";
        String errorCode = "404";

        CompradorServiceException exception = new CompradorServiceException(message, errorCode);

        assertEquals(message, exception.getMessage());
        assertEquals(errorCode, exception.getErrorCode());
    }

    @Test
    void testIsRuntimeException() {
        CompradorServiceException exception = new CompradorServiceException("Test", "500");
        assertTrue(exception instanceof RuntimeException);
    }
}
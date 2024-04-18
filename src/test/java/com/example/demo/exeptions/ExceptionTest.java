package com.example.demo.exeptions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
 class ExceptionTest {

    @InjectMocks
    private ExceptionsHandler exceptionsHandler;

    @Test
     void testHandleRuntimeException() {
        // Arrange
        RuntimeException ex = new RuntimeException("Test runtime exception");

        // Act
        ErrorMessage result = exceptionsHandler.runtimeError(ex);

        // Assert
        assertNotNull(result);
        assertEquals("Internal server error", result.message());
    }

    @Test
     void testHandleNotFoundException() {
        // Arrange
        ResponseStatusException ex = new ResponseStatusException(HttpStatus.NOT_FOUND, "Test not found exception");

        // Act
        ErrorMessage result = exceptionsHandler.notFoundException(ex);

        // Assert
        assertNotNull(result);
        assertEquals("Resource not found", result.message());
    }

    @Test
     void testHandleBadRequestException() {
        // Arrange
        HttpClientErrorException ex = new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Test bad request exception");

        // Act
        ErrorMessage result = exceptionsHandler.handleBadRequestException(ex);

        // Assert
        assertNotNull(result);
        assertEquals("Bad request", result.message());
    }

    @Test
     void testHandleMethodNotAllowed() {
        // Arrange
        HttpRequestMethodNotSupportedException ex = new HttpRequestMethodNotSupportedException("GET");

        // Act
        ErrorMessage result = exceptionsHandler.handleMethodNotAllowed(ex);

        // Assert
        assertNotNull(result);
        assertEquals("Method not allowed", result.message());
    }
}

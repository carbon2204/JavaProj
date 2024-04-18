package com.example.demo.exeptions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(MockitoExtension.class)
 class ExceptionTest {

  @InjectMocks
  private ExceptionsHandler exceptionsHandler;

  @Test
  void testHandleRuntimeException() {
    RuntimeException ex = new RuntimeException("Test runtime exception");

    ErrorMessage result = exceptionsHandler.runtimeError(ex);

    assertNotNull(result);
    assertEquals("Internal server error", result.message());
  }

  @Test
  void testHandleNotFoundException() {
    ResponseStatusException ex = new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Test not found exception");

    ErrorMessage result = exceptionsHandler.notFoundException(ex);

    assertNotNull(result);
    assertEquals("Resource not found", result.message());
  }

  @Test
  void testHandleBadRequestException() {
    HttpClientErrorException ex = new HttpClientErrorException(HttpStatus.BAD_REQUEST,
            "Test bad request exception");

    ErrorMessage result = exceptionsHandler.handleBadRequestException(ex);

    assertNotNull(result);
    assertEquals("Bad request", result.message());
  }

  @Test
  void testHandleMethodNotAllowed() {
    HttpRequestMethodNotSupportedException ex = new HttpRequestMethodNotSupportedException("GET");

    ErrorMessage result = exceptionsHandler.handleMethodNotAllowed(ex);

    assertNotNull(result);
    assertEquals("Method not allowed", result.message());
  }
}

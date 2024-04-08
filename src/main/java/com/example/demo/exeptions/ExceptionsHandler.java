package com.example.demo.exeptions;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * The type Exceptions handler.
 */
@Slf4j
@RestControllerAdvice
public class ExceptionsHandler {
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(RuntimeException.class)
  public ErrorMessage runtimeError(Exception ex) {
    log.error("Runtime exception", ex);
    return new ErrorMessage(ex.getMessage());
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler({ResponseStatusException.class, NoHandlerFoundException.class})
  public ErrorMessage notFoundException(Exception ex) {
    log.error("404 NotFound exception");
    return new ErrorMessage("Resource not found");
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({
    HttpClientErrorException.class,
    HttpMessageNotReadableException.class,
    MethodArgumentNotValidException.class,
    MissingServletRequestParameterException.class,
    ConstraintViolationException.class
  })
  public ErrorMessage handleBadRequestException(Exception ex) {
    log.error("400 error");
    return new ErrorMessage("Bad request");
  }

  /**
     * Handle method not allowed error message.
     *
     * @param ex the ex
     * @return the error message
    */
  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
  @ExceptionHandler({HttpRequestMethodNotSupportedException.class, MethodNotAllowedException.class})
  public ErrorMessage handleMethodNotAllowed(Exception ex) {
    log.error("MethodNotAllowed exceptionZZZZZ");
    return new ErrorMessage("Method not allowed");
  }

}
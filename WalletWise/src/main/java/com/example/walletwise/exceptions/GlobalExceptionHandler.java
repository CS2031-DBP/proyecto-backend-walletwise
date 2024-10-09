package com.example.walletwise.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
    return createErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
    return createErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
  }

  @ExceptionHandler(InsufficientFundsException.class)
  public ResponseEntity<ErrorResponse> handleInsufficientFundsException(InsufficientFundsException ex) {
    return createErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
  }

  @ExceptionHandler(UnauthorizedActionException.class)
  public ResponseEntity<ErrorResponse> handleUnauthorizedActionException(UnauthorizedActionException ex) {
    return createErrorResponse(HttpStatus.FORBIDDEN, ex.getMessage());
  }

  @ExceptionHandler(UserAlreadyExistException.class)
  public ResponseEntity<Map<String, String>> handleUserAlreadyExistException(UserAlreadyExistException ex) {
    return createErrorMapResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<Map<String, String>> handleUsernameNotFoundException(UsernameNotFoundException ex) {
    return createErrorMapResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(UnauthorizeOperationException.class)
  public ResponseEntity<Map<String, String>> handleUnauthorizeOperationException(UnauthorizeOperationException ex) {
    return createErrorMapResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage()));
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<Map<String, String>> handleBadCredentialsException(BadCredentialsException ex) {
    return createErrorMapResponse("Email o contraseña incorrectos", HttpStatus.BAD_REQUEST);
  }

  // Manejo de excepciones generales
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, String>> handleGlobalException(Exception ex) {
    return createErrorMapResponse("Ocurrió un error inesperado", HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private ResponseEntity<ErrorResponse> createErrorResponse(HttpStatus status, String message) {
    ErrorResponse errorResponse = new ErrorResponse(status, message);
    return new ResponseEntity<>(errorResponse, status);
  }

  private ResponseEntity<Map<String, String>> createErrorMapResponse(String message, HttpStatus status) {
    Map<String, String> error = new HashMap<>();
    error.put("error", message);
    return new ResponseEntity<>(error, status);
  }
}

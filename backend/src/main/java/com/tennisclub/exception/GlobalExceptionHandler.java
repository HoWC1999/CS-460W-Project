package com.tennisclub.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.ResponseEntity;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleException(Exception ex) {
    // Log error using AuditService if needed
    return ResponseEntity.status(500).body("An error occurred: " + ex.getMessage());
  }
}

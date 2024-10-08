package com.example.walletwise.exceptions;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
  private int status;
  private String message;
  private long timestamp;

  public ErrorResponse(HttpStatus status, String message) {
    this.status = status.value();
    this.message = message;
    this.timestamp = System.currentTimeMillis();
  }

  // Getters y Setters
  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }
}
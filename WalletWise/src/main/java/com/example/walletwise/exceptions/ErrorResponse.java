package com.example.walletwise.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ErrorResponse {
  private int status;
  private String message;
  private long timestamp;

  public ErrorResponse(HttpStatus status, String message) {
    this.status = status.value();
    this.message = message;
    this.timestamp = System.currentTimeMillis();
  }
}
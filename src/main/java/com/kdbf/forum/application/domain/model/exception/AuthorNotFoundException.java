package com.kdbf.forum.application.domain.model.exception;

public class AuthorNotFoundException extends RuntimeException {
  public AuthorNotFoundException(String message) {
    super(message);
  }

}

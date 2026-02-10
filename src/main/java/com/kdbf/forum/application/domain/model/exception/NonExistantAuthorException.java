package com.kdbf.forum.application.domain.model.exception;

public class NonExistantAuthorException extends RuntimeException {
  public NonExistantAuthorException(String message) {
    super(message);
  }

}

package com.kdbf.forum.application.domain.model.exception;

public class AlreadyDeletedException extends RuntimeException {
  public AlreadyDeletedException(String message) {
    super(message);
  }

}

package com.kdbf.forum.application.domain.service.exception;

public class DuplicateAuthorException extends RuntimeException {
  public DuplicateAuthorException(String message) {
    super(message);
  }

}

package com.kdbf.forum.application.domain.model.exception;

public class DuplicateTopicException extends RuntimeException {
  public DuplicateTopicException(String message) {
    super(message);
  }
}

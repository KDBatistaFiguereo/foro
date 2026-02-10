package com.kdbf.forum.application.domain.model.exception;

public class NonExistantCourseException extends RuntimeException {
  public NonExistantCourseException(String message) {
    super(message);
  }
}

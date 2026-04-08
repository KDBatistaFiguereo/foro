package com.kdbf.forum.application.domain.model.exception;

public class CourseNotFoundException extends
    RuntimeException {
  public CourseNotFoundException(String message) {
    super(message);
  }

}

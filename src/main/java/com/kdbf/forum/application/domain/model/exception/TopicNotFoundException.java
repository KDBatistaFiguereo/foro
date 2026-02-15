package com.kdbf.forum.application.domain.model.exception;

public class TopicNotFoundException extends RuntimeException {
  public TopicNotFoundException(String message) {
    super(message);
  }

}

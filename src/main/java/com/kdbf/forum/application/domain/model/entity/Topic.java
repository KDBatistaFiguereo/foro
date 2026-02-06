package com.kdbf.forum.application.domain.model.entity;

import java.util.UUID;

import com.kdbf.forum.application.domain.model.exception.InvalidTitleException;
import com.kdbf.forum.application.domain.model.exception.NoBodyException;

public class Topic {
  private UUID publicId;
  private String title;
  private String body;
  private Author author;
  private Course course;

  private Topic(Course course, UUID publicId, String title, String body, Author author) {
    if (title == null || title.isEmpty()) {
      throw new InvalidTitleException("A topic needs to have a title");
    }
    if (body == null || body.isEmpty()) {
      throw new NoBodyException("A topic needs to have a body");
    }

    this.course = course;
    this.publicId = publicId;
    this.title = title;
    this.body = body;
    this.author = author;
  }

  public static Topic newInstance(
      Course course,
      String title,
      String body,
      Author author) {
    return new Topic(course, UUID.randomUUID(), title, body, author);
  }

  public static Topic reconstitute(
      Course course,
      UUID publicId,
      String title,
      String body,
      Author author) {

    return new Topic(course, publicId, title, body, author);
  }
}

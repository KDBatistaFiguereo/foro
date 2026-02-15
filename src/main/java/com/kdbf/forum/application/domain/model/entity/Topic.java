package com.kdbf.forum.application.domain.model.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.kdbf.forum.application.domain.model.entity.objectValue.TopicStatus;
import com.kdbf.forum.application.domain.model.exception.InvalidTitleException;
import com.kdbf.forum.application.domain.model.exception.NoBodyException;

import lombok.Getter;

@Getter
public class Topic {
  private final UUID publicId;
  private final String title;
  private final String body;
  private final Author author;
  private final Course course;
  private final LocalDateTime creationDate;

  private final TopicStatus status;

  private Topic(final Course course, final UUID publicId, final String title, final String body, final Author author,
      LocalDateTime creationDate, TopicStatus status) {
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
    this.creationDate = creationDate;
    this.status = status;
  }

  public static Topic newInstance(
      final Course course,
      final String title,
      final String body,
      final Author author) {
    return new Topic(
        course,
        UUID.randomUUID(),
        title,
        body,
        author,
        LocalDateTime.now(),
        TopicStatus.DRAFT);
  }

  public static Topic reconstitute(
      final Course course,
      final UUID publicId,
      final String title,
      final String body,
      final Author author,
      final LocalDateTime creationDate,
      final TopicStatus status) {

    return new Topic(
        course,
        publicId,
        title,
        body,
        author,
        creationDate,
        status);
  }
}

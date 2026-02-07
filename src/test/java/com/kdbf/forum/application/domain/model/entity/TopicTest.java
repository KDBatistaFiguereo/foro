package com.kdbf.forum.application.domain.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.kdbf.forum.application.domain.model.exception.InvalidTitleException;
import com.kdbf.forum.application.domain.model.exception.NoBodyException;

public class TopicTest {

  @Test
  public void shouldCreateTopic() {
    String title = "What is a code smell?";
    String body = "I always see this term thrown around here. Any help?";
    Author author = new Author("confused_programmer");
    Course course = new Course("Programming 014");

    Topic topic = Topic.newInstance(course, title, body, author);

    assertEquals(title, topic.getTitle());
    assertNotNull(topic.getPublicId());
    assertEquals(body, topic.getBody());
    assertEquals(author, topic.getAuthor());
    assertEquals(course, topic.getCourse());
  }

  @Test
  public void shouldUpdateTopic() {
    String title = "What is a code smell?";
    String body = "I always see this term thrown around here. Any help?";
    Author author = new Author("confused_programmer");
    Course course = new Course("Programming 014");

    Topic topic = Topic.newInstance(course, title, body, author);

    String updatedBody = "I always see this term in the forum";
    UUID topicId = topic.getPublicId();

    topic = Topic.reconstitute(
        topic.getCourse(),
        topicId,
        topic.getTitle(),
        updatedBody,
        topic.getAuthor());

    assertEquals(topicId, topic.getPublicId());
    assertEquals(updatedBody, topic.getBody());
  }

  public void shouldNotAllowEmptyTitle() {
    InvalidTitleException exception = assertThrows(InvalidTitleException.class, () -> {
      Topic.newInstance(new Course("CS-104"),
          "",
          "Hello guys",
          new Author("empty_title_lover"));
    });

    assertEquals("A topic needs to have a title", exception.getMessage());
  }

  public void shouldNotAllowEmptyBody() {
    NoBodyException exception = assertThrows(NoBodyException.class, () -> {
      Topic.newInstance(new Course("CS-202"),
          "Do i really need to add a body to my post?",
          "",
          new Author("text_body_hater"));
    });

    assertEquals("A topic needs to have a body", exception.getMessage());
  }
}

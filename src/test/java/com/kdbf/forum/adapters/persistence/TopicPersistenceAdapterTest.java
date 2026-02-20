package com.kdbf.forum.adapters.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import com.kdbf.forum.adapters.out.persistence.TopicPersistenceAdapter;
import com.kdbf.forum.adapters.out.persistence.entity.AuthorJpa;
import com.kdbf.forum.adapters.out.persistence.entity.CourseJpa;
import com.kdbf.forum.adapters.out.persistence.entity.TopicJpa;
import com.kdbf.forum.adapters.out.persistence.repository.AuthorRepository;
import com.kdbf.forum.adapters.out.persistence.repository.CourseRepository;
import com.kdbf.forum.adapters.out.persistence.repository.TopicRepository;
import com.kdbf.forum.application.domain.model.entity.Author;
import com.kdbf.forum.application.domain.model.entity.Course;
import com.kdbf.forum.application.domain.model.entity.Topic;
import com.kdbf.forum.application.domain.model.entity.objectValue.TopicStatus;
import com.kdbf.forum.infraestructure.security.SecurityConfig;

import jakarta.transaction.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
@Import(SecurityConfig.class)
public class TopicPersistenceAdapterTest {

  @Autowired
  AuthorRepository authorRepository;

  @Autowired
  CourseRepository courseRepository;

  @Autowired
  TopicRepository topicRepository;

  @Autowired
  TopicPersistenceAdapter topicAdapter;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Test
  @Transactional
  public void shouldCreateNewTopic() {
    AuthorJpa authorJpa = new AuthorJpa("junior_coder",
        "junior@gmail.com",
        passwordEncoder.encode("securePassword"));
    CourseJpa courseJpa = new CourseJpa("CS-014");
    authorRepository.save(authorJpa);
    courseRepository.save(courseJpa);

    Author author = new Author("junior_coder");
    Course course = new Course("CS-014");
    Topic topic = Topic.newInstance(course, "What is an Optional?", "Im new to this concept", author);
    Topic savedTopic = topicAdapter.persistTopic(topic);

    assertNotNull(savedTopic);
    assertNotNull(savedTopic.getPublicId());
    assertEquals(topic.getPublicId(), savedTopic.getPublicId());
    assertEquals(topic.getTitle(), savedTopic.getTitle());
    assertEquals(topic.getAuthor().getDisplayName(), savedTopic.getAuthor().getDisplayName());
    assertEquals(topic.getBody(), savedTopic.getBody());
    assertEquals(topic.getCourse().getCourseName(), savedTopic.getCourse().getCourseName());

  }

  @Test
  @Transactional
  public void shouldUpdateTopic() {
    AuthorJpa authorJpa = new AuthorJpa("new_coder",
        "newcoder@gmail.com",
        passwordEncoder.encode("securePassword"));
    CourseJpa courseJpa = new CourseJpa("CS-015");
    TopicJpa topicJpa = new TopicJpa(
        UUID.randomUUID(),
        "What is an Optional?",
        "Im new to this concept",
        authorJpa,
        courseJpa,
        TopicStatus.DRAFT,
        LocalDateTime.now());
    authorRepository.save(authorJpa);
    courseRepository.save(courseJpa);
    topicRepository.save(topicJpa);

    Author author = new Author("new_coder");
    Course course = new Course("CS-015");
    Topic topic = Topic.reconstitute(
        course,
        topicJpa.getPublicId(),
        "What is an Optional?",
        "I am now changing the body of my topic",
        author,
        topicJpa.getCreationDate(),
        topicJpa.getStatus());

    topicAdapter.persistTopic(topic);

    Optional<TopicJpa> savedTopic = topicRepository.byPublicId(topicJpa.getPublicId());

    assertFalse(savedTopic.isEmpty());
    assertEquals(topic.getBody(), savedTopic.get().getBody());
  }

}

package com.kdbf.forum.adapters.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.kdbf.forum.adapters.in.security.JwtSecurityFilter;
import com.kdbf.forum.adapters.in.security.service.TokenService;
import com.kdbf.forum.adapters.out.persistence.TopicPersistenceAdapter;
import com.kdbf.forum.adapters.out.persistence.entity.AuthorJpa;
import com.kdbf.forum.adapters.out.persistence.entity.CourseJpa;
import com.kdbf.forum.adapters.out.persistence.entity.TopicJpa;
import com.kdbf.forum.adapters.out.persistence.mapper.TopicJpaMapper;
import com.kdbf.forum.adapters.out.persistence.repository.AuthorRepository;
import com.kdbf.forum.adapters.out.persistence.repository.CourseRepository;
import com.kdbf.forum.adapters.out.persistence.repository.TopicRepository;
import com.kdbf.forum.application.domain.model.entity.Author;
import com.kdbf.forum.application.domain.model.entity.Course;
import com.kdbf.forum.application.domain.model.entity.Topic;
import com.kdbf.forum.application.domain.model.entity.objectValue.CourseCode;
import com.kdbf.forum.application.domain.model.entity.objectValue.TopicStatus;

import jakarta.transaction.Transactional;

@ActiveProfiles("test")
@SpringBootTest
@Testcontainers
public class TopicPersistenceAdapterTest {

  @Autowired
  AuthorRepository authorRepository;

  @Autowired
  CourseRepository courseRepository;

  @Autowired
  TopicRepository topicRepository;

  @Autowired
  TopicPersistenceAdapter topicAdapter;

  @MockitoBean
  private TokenService tokenService;

  @MockitoBean
  private JwtSecurityFilter securityFilter;

  @MockitoBean
  private TopicJpaMapper topicJpaMapper;

  @BeforeEach
  void setUp() {
    when(topicJpaMapper.toDomain(any(), any())).thenAnswer(invocation -> {
      TopicJpa jpa = invocation.getArgument(0);
      return Topic.reconstitute(
          new Course(jpa.getCourse().getCourseName(), jpa.getCourse().getCourseCode()),
          jpa.getPublicId(),
          jpa.getTitle(),
          jpa.getBody(),
          new Author(jpa.getAuthor().getDisplayName()),
          jpa.getCreationDate(),
          jpa.getStatus());
    });
    when(topicJpaMapper.toJpa(any(), any())).thenAnswer(invocation -> {
      Topic topic = invocation.getArgument(0);
      return new TopicJpa(
          topic.getPublicId(),
          topic.getTitle(),
          topic.getBody(),
          null,
          null,
          topic.getStatus(),
          topic.getCreationDate());
    });
    doAnswer(invocation -> {
      Topic topic = invocation.getArgument(0);
      TopicJpa jpa = invocation.getArgument(1);
      jpa.setTitle(topic.getTitle());
      jpa.setBody(topic.getBody());
      jpa.setStatus(topic.getStatus());
      return null;
    }).when(topicJpaMapper).updateJpaFromDomain(any(), any(), any());
  }

  @Test
  @Transactional
  public void shouldCreateNewTopic() {
    AuthorJpa authorJpa = new AuthorJpa("junior_coder",
        "junior@gmail.com",
        "$2a$12$lrcBEEymSMC5ipTNgpz8gOxCMU/VAiuaXEgqDka1VCOJYKVPE.uhe");
    CourseJpa courseJpa = new CourseJpa("Computer Science Fundamentals", new CourseCode("CSF-0014"));
    authorRepository.save(authorJpa);
    courseRepository.save(courseJpa);

    Author author = new Author("junior_coder");
    Course course = new Course("Computer Science Fundamentals", new CourseCode("CSF-0014"));
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
        "$2a$12$lrcBEEymSMC5ipTNgpz8gOxCMU/VAiuaXEgqDka1VCOJYKVPE.uhe");
    CourseJpa courseJpa = new CourseJpa("Introduction to Java programming", new CourseCode("JAV-0014"));
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
    Course course = new Course("Introduction to Java programming", new CourseCode("JAV-0014"));
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

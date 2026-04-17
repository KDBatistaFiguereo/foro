package com.kdbf.forum.adapters.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.kdbf.forum.adapters.out.persistence.AuthorPersistenceAdapter;
import com.kdbf.forum.adapters.out.persistence.CoursePersistenceAdapter;
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
import com.kdbf.forum.application.domain.model.entity.objectValue.TopicStatus;
import com.kdbf.forum.mother.AuthorJpaMother;
import com.kdbf.forum.mother.AuthorMother;
import com.kdbf.forum.mother.CourseJpaMother;
import com.kdbf.forum.mother.CourseMother;

import jakarta.transaction.Transactional;

@ActiveProfiles("test")
@Testcontainers
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@Tag("context")
@AutoConfigureMockMvc(addFilters = false)
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
  TopicJpaMapper topicMapper;

  @Autowired
  CoursePersistenceAdapter courseAdapter;

  @Autowired
  AuthorPersistenceAdapter authorAdapter;

  @Test
  @Transactional
  public void shouldCreateNewTopic() {
    Author author = AuthorMother.sample();
    Course course = CourseMother.sample();
    courseAdapter.persistCourse(course);
    authorAdapter.registerAuthor(author,
        "johndoe@gmail.com",
        "hashedSecret");

    Topic topic = Topic.newInstance(course,
        "What is an Optional?",
        "Im new to this concept",
        author);
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
    AuthorJpa authorJpa = AuthorJpaMother.sampleWithNameAndHandle(
        "John Doe", "johndoe");
    CourseJpa courseJpa = CourseJpaMother.customSample(
        "Programing basics",
        "CSA-0015");
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

    Author author = AuthorMother.customSample("John Doe", "johndoe");
    Course course = CourseMother.customSample("Programing basics", "CSA-0015");
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

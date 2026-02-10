package com.kdbf.forum.adapters.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.kdbf.forum.adapters.out.persistence.TopicPersistenceAdapter;
import com.kdbf.forum.adapters.out.persistence.entity.AuthorJpa;
import com.kdbf.forum.adapters.out.persistence.entity.CourseJpa;
import com.kdbf.forum.adapters.out.persistence.repository.AuthorRepository;
import com.kdbf.forum.adapters.out.persistence.repository.CourseRepository;
import com.kdbf.forum.application.domain.model.entity.Author;
import com.kdbf.forum.application.domain.model.entity.Course;
import com.kdbf.forum.application.domain.model.entity.Topic;
import jakarta.transaction.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
public class TopicPersistenceAdapterTest {

  @Autowired
  AuthorRepository authorRepository;

  @Autowired
  CourseRepository courseRepository;

  @Autowired
  TopicPersistenceAdapter topicAdapter;

  @Test
  @Transactional
  public void shouldCreateNewTopic() {
    AuthorJpa authorJpa = new AuthorJpa("junior_coder");
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
    assertEquals(topic.getAuthor().getUsername(), savedTopic.getAuthor().getUsername());
    assertEquals(topic.getBody(), savedTopic.getBody());
    assertEquals(topic.getCourse().getCourseName(), savedTopic.getCourse().getCourseName());

  }

}

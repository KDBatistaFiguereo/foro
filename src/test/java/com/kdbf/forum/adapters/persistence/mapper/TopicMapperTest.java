package com.kdbf.forum.adapters.persistence.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.kdbf.forum.adapters.out.persistence.entity.AuthorJpa;
import com.kdbf.forum.adapters.out.persistence.entity.CourseJpa;
import com.kdbf.forum.adapters.out.persistence.entity.TopicJpa;
import com.kdbf.forum.adapters.out.persistence.mapper.AuthorJpaMapper;
import com.kdbf.forum.adapters.out.persistence.mapper.AuthorJpaMapperImpl;
import com.kdbf.forum.adapters.out.persistence.mapper.CourseJpaMapper;
import com.kdbf.forum.adapters.out.persistence.mapper.CourseJpaMapperImpl;
import com.kdbf.forum.adapters.out.persistence.mapper.TopicJpaMapper;
import com.kdbf.forum.adapters.out.persistence.mapper.TopicJpaMapperImpl;
import com.kdbf.forum.adapters.out.persistence.utility.CycleAvoidingMappingContext;
import com.kdbf.forum.application.domain.model.entity.Author;
import com.kdbf.forum.application.domain.model.entity.Course;
import com.kdbf.forum.application.domain.model.entity.Topic;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TopicJpaMapperImpl.class,
    AuthorJpaMapperImpl.class,
    CourseJpaMapperImpl.class })
public class TopicMapperTest {

  @Autowired
  private AuthorJpaMapper authorMapper;

  @Autowired
  private CourseJpaMapper courseMapper;

  @Autowired
  private TopicJpaMapper topicMapper;

  private CycleAvoidingMappingContext context;

  @BeforeEach
  public void setUp() {
    context = new CycleAvoidingMappingContext();
  }

  @Test
  public void shouldConvertToDomain() {
    TopicJpa topicJpa = new TopicJpa(UUID.randomUUID(),
        "How does mapping work",
        "I want to understand mapping",
        new AuthorJpa("new programmer"),
        new CourseJpa("CS-014"));

    Topic topic = topicMapper.toDomain(topicJpa, context);

    assertNotNull(topic);
    assertEquals(topicJpa.getPublicId(), topic.getPublicId());
    assertEquals(topicJpa.getTitle(), topic.getTitle());
    assertEquals(topicJpa.getBody(), topic.getBody());
    assertEquals(topicJpa.getAuthor().getUsername(), topic.getAuthor().getUsername());
    assertEquals(topicJpa.getCourse().getCourseName(), topic.getCourse().getCourseName());
    assertNotNull(topic.getAuthor());
    assertNotNull(topic.getCourse());
  }

  @Test
  public void shouldConvertToJpa() {

    Topic topic = Topic.newInstance(
        new Course("CS-014"),
        "How does jpa work",
        "I cant wrap my head around it",
        new Author("persistence_hater"));

    TopicJpa topicJpa = topicMapper.toJpa(topic, context);

    assertNotNull(topicJpa);
    assertEquals(topic.getPublicId(), topicJpa.getPublicId());
    assertEquals(topic.getTitle(), topicJpa.getTitle());
    assertEquals(topic.getBody(), topicJpa.getBody());
    assertEquals(topic.getCourse().getCourseName(), topicJpa.getCourse().getCourseName());
    assertEquals(topic.getAuthor().getUsername(), topicJpa.getAuthor().getUsername());
    assertNotNull(topicJpa.getAuthor());
    assertNotNull(topicJpa.getCourse());
  }

}

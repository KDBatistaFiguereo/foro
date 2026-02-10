package com.kdbf.forum.adapters.out.persistence;

import org.springframework.stereotype.Repository;

import com.kdbf.forum.adapters.out.persistence.entity.AuthorJpa;
import com.kdbf.forum.adapters.out.persistence.entity.CourseJpa;
import com.kdbf.forum.adapters.out.persistence.entity.TopicJpa;
import com.kdbf.forum.adapters.out.persistence.mapper.TopicJpaMapper;
import com.kdbf.forum.adapters.out.persistence.repository.AuthorRepository;
import com.kdbf.forum.adapters.out.persistence.repository.CourseRepository;
import com.kdbf.forum.adapters.out.persistence.repository.TopicRepository;
import com.kdbf.forum.adapters.out.persistence.utility.CycleAvoidingMappingContext;
import com.kdbf.forum.application.domain.model.entity.Topic;
import com.kdbf.forum.application.domain.model.exception.DuplicateTopicException;
import com.kdbf.forum.application.domain.model.exception.NonExistantAuthorException;
import com.kdbf.forum.application.domain.model.exception.NonExistantCourseException;
import com.kdbf.forum.application.port.out.PersistTopicsPort;
import com.kdbf.forum.application.port.out.TopicsExistencePort;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class TopicPersistenceAdapter implements
    PersistTopicsPort, TopicsExistencePort {

  private final TopicRepository topicRepository;
  private final AuthorRepository authorRepository;
  private final CourseRepository courseRepository;
  private final TopicJpaMapper topicMapper;

  @Override
  @Transactional
  public Topic persistTopic(Topic topic) {
    CycleAvoidingMappingContext context = new CycleAvoidingMappingContext();
    AuthorJpa authorJpa = authorRepository.byUserName(topic.getAuthor().getUsername())
        .orElseThrow(() -> new NonExistantAuthorException("The user doesn't exist"));
    CourseJpa courseJpa = courseRepository.byCourseName(topic.getCourse().getCourseName())
        .orElseThrow(() -> new NonExistantCourseException("The course doesn't exist"));
    TopicJpa entityToSave = topicMapper.toJpa(topic, context);

    entityToSave.setAuthor(authorJpa);
    entityToSave.setCourse(courseJpa);

    if (existsByTitleAndCourseName(entityToSave.getTitle(),
        entityToSave.getCourse().getCourseName())) {
      throw new DuplicateTopicException("This topic already exists");
    } else {
      entityToSave = topicRepository.save(entityToSave);
    }

    return topicMapper.toDomain(entityToSave, context);
  }

  @Override
  public Boolean existsByTitleAndCourseName(String title, String courseName) {
    return topicRepository.exists(title, courseName);
  }

}

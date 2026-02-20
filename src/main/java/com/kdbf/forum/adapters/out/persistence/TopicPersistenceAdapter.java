package com.kdbf.forum.adapters.out.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
import com.kdbf.forum.application.port.out.FindTopicsPort;
import com.kdbf.forum.application.port.out.PersistTopicsPort;
import com.kdbf.forum.application.port.out.TopicsExistencePort;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class TopicPersistenceAdapter implements
    PersistTopicsPort, TopicsExistencePort, FindTopicsPort {

  private final TopicRepository topicRepository;
  private final AuthorRepository authorRepository;
  private final CourseRepository courseRepository;
  private final TopicJpaMapper topicMapper;
  private final CycleAvoidingMappingContext context = new CycleAvoidingMappingContext();

  @Override
  @Transactional
  public Topic persistTopic(Topic topic) {

    return topicRepository.byPublicId(topic.getPublicId())
        .map(existingEntity -> {
          topicMapper.updateJpaFromDomain(topic, existingEntity, context);

          TopicJpa saved = topicRepository.save(existingEntity);
          return topicMapper.toDomain(saved, context);
        }).orElseGet(() -> {

          AuthorJpa authorJpa = authorRepository.findByDisplayName(topic.getAuthor().getDisplayName())
              .orElseThrow(() -> new NonExistantAuthorException("The user doesn't exist"));
          CourseJpa courseJpa = courseRepository.byCourseName(topic.getCourse().getCourseName())
              .orElseThrow(() -> new NonExistantCourseException("The course doesn't exist"));

          if (existsByTitleAndCourseName(topic.getTitle(), topic.getCourse().getCourseName())) {
            throw new DuplicateTopicException("This topic already exists");
          }

          TopicJpa newEntity = topicMapper.toJpa(topic, context);
          newEntity.setAuthor(authorJpa);
          newEntity.setCourse(courseJpa);

          TopicJpa saved = topicRepository.save(newEntity);
          return topicMapper.toDomain(saved, context);

        });
  }

  @Override
  public Boolean existsByTitleAndCourseName(String title, String courseName) {
    return topicRepository.exists(title, courseName);
  }

  @Override
  public List<Topic> findAll() {
    return topicRepository.findAll().stream()
        .map(x -> topicMapper.toDomain(x, context))
        .toList();
  }

  @Override
  public Optional<Topic> byPublicId(UUID publicId) {
    return topicRepository.byPublicId(publicId)
        .map(x -> topicMapper.toDomain(x, context));
  }

  @Override
  public Boolean existsByPublicId(UUID publicId) {
    return topicRepository.existsByPublicId(publicId);
  }

}

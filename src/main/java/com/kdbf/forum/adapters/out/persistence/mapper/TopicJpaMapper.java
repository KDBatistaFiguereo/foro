package com.kdbf.forum.adapters.out.persistence.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.kdbf.forum.adapters.out.persistence.entity.TopicJpa;
import com.kdbf.forum.adapters.out.persistence.utility.CycleAvoidingMappingContext;
import com.kdbf.forum.application.domain.model.entity.Topic;

@Mapper(componentModel = "spring", uses = { AuthorJpaMapper.class, CourseJpaMapper.class })
public abstract class TopicJpaMapper {

  @Autowired
  protected AuthorJpaMapper authorJpaMapper;

  @Autowired
  protected CourseJpaMapper courseJpaMapper;

  @ObjectFactory
  protected Topic createTopic(TopicJpa topicJpa, @Context CycleAvoidingMappingContext context) {
    return Topic.reconstitute(
        courseJpaMapper.toDomain(topicJpa.getCourse(), context),
        topicJpa.getPublicId(),
        topicJpa.getTitle(),
        topicJpa.getBody(),
        authorJpaMapper.toDomain(topicJpa.getAuthor(), context));
  }

  abstract Topic toDomain(TopicJpa topicJpa, @Context CycleAvoidingMappingContext context);

  abstract TopicJpa toJpa(Topic topic, @Context CycleAvoidingMappingContext context);
}

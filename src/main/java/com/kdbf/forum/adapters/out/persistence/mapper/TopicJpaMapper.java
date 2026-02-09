package com.kdbf.forum.adapters.out.persistence.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;

import com.kdbf.forum.adapters.out.persistence.entity.TopicJpa;
import com.kdbf.forum.adapters.out.persistence.utility.CycleAvoidingMappingContext;
import com.kdbf.forum.application.domain.model.entity.Topic;

@Mapper(componentModel = "spring", uses = { AuthorJpaMapper.class, CourseJpaMapper.class })
public interface TopicJpaMapper {

  Topic toDomain(TopicJpa topicJpa, @Context CycleAvoidingMappingContext context);

  TopicJpa toJpa(Topic topic, @Context CycleAvoidingMappingContext context);

}

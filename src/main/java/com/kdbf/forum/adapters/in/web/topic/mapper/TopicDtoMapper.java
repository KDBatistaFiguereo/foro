package com.kdbf.forum.adapters.in.web.topic.mapper;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.kdbf.forum.adapters.in.web.author.mapper.AuthorDtoMapper;
import com.kdbf.forum.adapters.in.web.course.mapper.CourseDtoMapper;
import com.kdbf.forum.adapters.in.web.topic.dto.CreateTopicDto;
import com.kdbf.forum.adapters.in.web.topic.dto.ResponseTopicDto;
import com.kdbf.forum.application.domain.model.entity.Topic;

@Mapper(componentModel = "spring", uses = { CourseDtoMapper.class, AuthorDtoMapper.class })
public abstract class TopicDtoMapper {

  @Autowired
  protected AuthorDtoMapper authorMapper;

  @Autowired
  protected CourseDtoMapper courseMapper;

  public Topic toDomain(CreateTopicDto topicDto) {
    return Topic.newInstance(
        courseMapper.toDomain(topicDto.course()),
        topicDto.title(),
        topicDto.body(),
        authorMapper.toDomain(topicDto.author()));
  }

  public abstract ResponseTopicDto toDto(Topic topic);

}

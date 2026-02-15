package com.kdbf.forum.adapters.web.mother;

import com.kdbf.forum.adapters.in.web.dto.AuthorDto;
import com.kdbf.forum.adapters.in.web.dto.CourseDto;
import com.kdbf.forum.adapters.in.web.dto.ResponseTopicDto;
import com.kdbf.forum.application.domain.model.entity.Topic;

public class TopicDtoMother {
  public static ResponseTopicDto sample(Topic topic) {
    return new ResponseTopicDto(
        topic.getPublicId(),
        topic.getTitle(),
        topic.getTitle(),
        new AuthorDto(topic.getAuthor().getUsername()),
        new CourseDto(topic.getCourse().getCourseName()),
        topic.getCreationDate(),
        topic.getStatus());
  }

}

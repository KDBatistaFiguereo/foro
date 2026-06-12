package com.kdbf.forum.adapters.web.mother;

import com.kdbf.forum.adapters.in.web.author.dto.AuthorDto;
import com.kdbf.forum.adapters.in.web.course.dto.CourseDto;
import com.kdbf.forum.adapters.in.web.topic.dto.ResponseTopicDto;
import com.kdbf.forum.application.domain.model.entity.Topic;

public class TopicDtoMother {
  public static ResponseTopicDto sample(Topic topic) {
    return new ResponseTopicDto(
        topic.getPublicId(),
        topic.getTitle(),
        topic.getBody(),
        new AuthorDto(topic.getAuthor().getDisplayName(), topic.getAuthor().getHandle()),
        new CourseDto(topic.getCourse().getCourseName(),
            topic.getCourse().getCourseCode().code()),
        topic.getCreationDate(),
        topic.getStatus());
  }

}

package com.kdbf.forum.adapters.in.web.topic.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.kdbf.forum.adapters.in.web.author.dto.AuthorDto;
import com.kdbf.forum.adapters.in.web.course.dto.CourseDto;
import com.kdbf.forum.application.domain.model.entity.objectValue.TopicStatus;

public record ResponseTopicDto(
    UUID publicId,
    String title,
    String body,
    AuthorDto author,
    CourseDto course,
    LocalDateTime creationDate,
    TopicStatus status) {
}

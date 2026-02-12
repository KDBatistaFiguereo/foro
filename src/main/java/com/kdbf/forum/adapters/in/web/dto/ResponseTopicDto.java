package com.kdbf.forum.adapters.in.web.dto;

import java.util.UUID;

public record ResponseTopicDto(
    UUID publicId,
    String title,
    String body,
    AuthorDto author,
    CourseDto course) {
}

package com.kdbf.forum.adapters.in.web.topic.dto;

import com.kdbf.forum.adapters.in.web.author.dto.AuthorDto;
import com.kdbf.forum.adapters.in.web.course.dto.CourseDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateTopicDto(
    @NotBlank String title,
    @NotBlank String body,
    @NotNull @Valid CourseDto course,
    @NotNull @Valid AuthorDto author) {
}

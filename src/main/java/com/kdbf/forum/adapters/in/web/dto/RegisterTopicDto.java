package com.kdbf.forum.adapters.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterTopicDto(
    @NotBlank @NotNull String title,
    @NotBlank @NotNull String body,
    @NotBlank @NotNull CourseDto course,
    @NotNull @NotNull AuthorDto author) {
}

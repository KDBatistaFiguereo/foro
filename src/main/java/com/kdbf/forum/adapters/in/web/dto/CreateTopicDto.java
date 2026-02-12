package com.kdbf.forum.adapters.in.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateTopicDto(
    @NotBlank String title,
    @NotBlank String body,
    @NotNull @Valid CourseDto course,
    @NotNull @Valid AuthorDto author) {
}

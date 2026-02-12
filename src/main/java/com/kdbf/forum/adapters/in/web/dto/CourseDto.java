package com.kdbf.forum.adapters.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CourseDto(
    @NotNull @NotBlank String courseName) {
}

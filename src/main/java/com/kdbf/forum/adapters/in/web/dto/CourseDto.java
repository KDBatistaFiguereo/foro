package com.kdbf.forum.adapters.in.web.dto;

import jakarta.validation.constraints.NotBlank;

public record CourseDto(
    @NotBlank String courseName,
    @NotBlank String courseCode) {
}

package com.kdbf.forum.adapters.in.web.course.dto;

import jakarta.validation.constraints.NotBlank;

public record CourseDto(
    @NotBlank String courseName,
    @NotBlank String courseCode) {
}

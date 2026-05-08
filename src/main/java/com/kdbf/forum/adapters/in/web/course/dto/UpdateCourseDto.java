package com.kdbf.forum.adapters.in.web.course.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateCourseDto(
    @NotBlank String newName) {
}

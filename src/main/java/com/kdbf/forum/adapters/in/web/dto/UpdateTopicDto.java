package com.kdbf.forum.adapters.in.web.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateTopicDto(
    @NotNull UUID publicId,
    @NotBlank String title,
    @NotBlank String body) {
}

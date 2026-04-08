package com.kdbf.forum.adapters.in.web.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthorDto(
    @NotBlank String displayName,
    @NotBlank String handle) {
}

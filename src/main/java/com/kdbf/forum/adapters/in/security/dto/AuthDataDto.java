package com.kdbf.forum.adapters.in.security.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthDataDto(
    @NotBlank String username, // email
    @NotBlank String password) {

}

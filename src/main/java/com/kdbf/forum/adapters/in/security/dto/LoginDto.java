package com.kdbf.forum.adapters.in.security.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginDto(
    @NotBlank String username, // email
    @NotBlank String password) {

}

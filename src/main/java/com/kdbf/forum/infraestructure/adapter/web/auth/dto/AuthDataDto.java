package com.kdbf.forum.infraestructure.adapter.web.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthDataDto(
    @NotBlank String username, // email
    @NotBlank String password) {

}

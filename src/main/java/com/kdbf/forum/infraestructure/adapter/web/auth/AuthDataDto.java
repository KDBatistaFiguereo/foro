package com.kdbf.forum.infraestructure.adapter.web.auth;

import jakarta.validation.constraints.NotBlank;

public record AuthDataDto(
    @NotBlank String login, // email
    @NotBlank String password) {

}

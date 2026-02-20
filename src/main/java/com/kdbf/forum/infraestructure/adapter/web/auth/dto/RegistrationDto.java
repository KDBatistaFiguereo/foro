package com.kdbf.forum.infraestructure.adapter.web.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegistrationDto(
    @NotBlank String displayName,
    @NotBlank @Email String username,
    @NotBlank String password) {

}

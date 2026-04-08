package com.kdbf.forum.adapters.in.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegistrationDto(
    @NotBlank String handle,
    @NotBlank String displayName,
    @NotBlank @Email String username,
    @NotBlank String password) {

}

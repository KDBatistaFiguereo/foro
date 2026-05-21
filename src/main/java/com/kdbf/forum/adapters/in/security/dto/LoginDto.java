package com.kdbf.forum.adapters.in.security.dto;

import jakarta.validation.constraints.NotBlank;

//TODO: change username to email
public record LoginDto(
    @NotBlank String username, // email
    @NotBlank String password) {

}

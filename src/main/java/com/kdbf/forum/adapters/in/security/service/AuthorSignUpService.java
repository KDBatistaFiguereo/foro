package com.kdbf.forum.adapters.in.security.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kdbf.forum.adapters.in.security.dto.RegistrationDto;
import com.kdbf.forum.application.domain.model.entity.Author;
import com.kdbf.forum.application.port.out.author.AuthorRegistrationPort;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthorSignUpService {

  private PasswordEncoder encoder;
  private AuthorRegistrationPort registerAuthor;

  public void signUpUser(RegistrationDto dto) {

    Author author = new Author(
        dto.displayName(),
        dto.handle());

    registerAuthor.registerAuthor(
        author,
        dto.username(),
        encoder.encode(dto.password()));
  }

}

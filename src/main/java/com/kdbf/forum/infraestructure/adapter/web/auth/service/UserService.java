package com.kdbf.forum.infraestructure.adapter.web.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kdbf.forum.adapters.out.persistence.entity.AuthorJpa;
import com.kdbf.forum.adapters.out.persistence.repository.AuthorRepository;
import com.kdbf.forum.infraestructure.adapter.web.auth.dto.RegistrationDto;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

  private AuthorRepository authorRepository;
  private PasswordEncoder encoder;

  public void signUpUser(RegistrationDto dto) {
    AuthorJpa authorJpa = new AuthorJpa();
    authorJpa.setUsername(dto.username());
    authorJpa.setHashedPassword(encoder.encode(dto.password()));
    authorJpa.setDisplayName(dto.displayName());
    authorRepository.save(authorJpa);
  }

}

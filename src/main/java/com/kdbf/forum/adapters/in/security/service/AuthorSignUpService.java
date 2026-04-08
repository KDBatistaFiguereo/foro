package com.kdbf.forum.adapters.in.security.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kdbf.forum.adapters.in.security.dto.RegistrationDto;
import com.kdbf.forum.adapters.out.persistence.entity.AuthorJpa;
import com.kdbf.forum.adapters.out.persistence.repository.AuthorRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthorSignUpService {

  private AuthorRepository authorRepository;
  private PasswordEncoder encoder;

  public void signUpUser(RegistrationDto dto) {
    AuthorJpa authorJpa = new AuthorJpa();
    authorJpa.setHandle(dto.handle());
    authorJpa.setUsername(dto.username());
    authorJpa.setHashedPassword(encoder.encode(dto.password()));
    authorJpa.setDisplayName(dto.displayName());
    authorRepository.save(authorJpa);
  }

}

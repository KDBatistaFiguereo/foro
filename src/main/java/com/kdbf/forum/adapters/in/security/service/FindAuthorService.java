package com.kdbf.forum.adapters.in.security.service;

import org.springframework.stereotype.Service;

import com.kdbf.forum.adapters.out.persistence.entity.AuthorJpa;
import com.kdbf.forum.adapters.out.persistence.repository.AuthorRepository;
import com.kdbf.forum.application.domain.model.exception.AuthorNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FindAuthorService {

  AuthorRepository authorRepository;

  public AuthorJpa findByUsername(String userName) {
    return authorRepository.findByUsername(userName)
        .orElseThrow(() -> new AuthorNotFoundException("The author does not exist"));
  }

}

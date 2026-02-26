package com.kdbf.forum.infraestructure.adapter.web.auth.service;

import org.springframework.stereotype.Service;

import com.kdbf.forum.adapters.out.persistence.entity.AuthorJpa;
import com.kdbf.forum.adapters.out.persistence.repository.AuthorRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FindAuthorService {

  AuthorRepository authorRepository;

  public AuthorJpa findByUsername(String userName) {
    return authorRepository.findByUsername(userName).get();
  }

}

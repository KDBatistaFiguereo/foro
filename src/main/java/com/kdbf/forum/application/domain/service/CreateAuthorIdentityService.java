package com.kdbf.forum.application.domain.service;

import org.springframework.stereotype.Service;

import com.kdbf.forum.application.domain.model.entity.Author;
import com.kdbf.forum.application.domain.service.exception.DuplicateAuthorException;
import com.kdbf.forum.application.port.in.CreateAuthorIdentityCommand;
import com.kdbf.forum.application.port.in.CreateAuthorIdentityUseCase;
import com.kdbf.forum.application.port.out.AuthorExistencePort;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CreateAuthorIdentityService implements
    CreateAuthorIdentityUseCase {

  private final AuthorExistencePort authorExistence;

  @Override
  public Author createIdentity(CreateAuthorIdentityCommand command) {
    if (authorExistence.existsByHandle(command.handle())) {
      throw new DuplicateAuthorException("An author with this handle already exists");
    }

    return new Author(
        command.displayName(),
        command.handle());
  }

}

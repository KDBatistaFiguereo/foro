package com.kdbf.forum.application.domain.service;

import com.kdbf.forum.application.domain.model.entity.Author;
import com.kdbf.forum.application.port.in.CreateAuthorIdentityCommand;
import com.kdbf.forum.application.port.in.CreateAuthorIdentityUseCase;

public class CreateAuthorIdentityService implements
    CreateAuthorIdentityUseCase {

  @Override
  public Author createIdentity(CreateAuthorIdentityCommand command) {
    return new Author(
        command.displayName(),
        command.handle());
  }

}

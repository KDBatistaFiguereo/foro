package com.kdbf.forum.application.port.in;

import com.kdbf.forum.application.domain.model.entity.Author;

public interface CreateAuthorIdentityUseCase {
  Author createIdentity(CreateAuthorIdentityCommand command);
}

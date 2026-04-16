package com.kdbf.forum.application.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import com.kdbf.forum.application.domain.model.entity.Author;
import com.kdbf.forum.application.port.in.CreateAuthorIdentityCommand;

public class CreateAuthorIdentityServiceTest {

  CreateAuthorIdentityService authorService;

  @BeforeEach
  void setUp() {
    authorService = new CreateAuthorIdentityService();
  }

  @Test
  void shouldReturnAuthorIdentity() {
    var command = new CreateAuthorIdentityCommand(
        "John Doe",
        "johndoe123");
    Author author = authorService.createIdentity(command);

    assertNotNull(author);
    assertEquals(command.displayName(), author.getDisplayName());
    assertEquals(command.handle(), author.getHandle());
  }
}

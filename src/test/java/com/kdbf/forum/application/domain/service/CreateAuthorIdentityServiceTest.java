package com.kdbf.forum.application.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kdbf.forum.application.domain.model.entity.Author;
import com.kdbf.forum.application.domain.service.exception.DuplicateAuthorException;
import com.kdbf.forum.application.port.in.CreateAuthorIdentityCommand;
import com.kdbf.forum.application.port.out.AuthorExistencePort;

@Tag("temp")
@ExtendWith(MockitoExtension.class)
public class CreateAuthorIdentityServiceTest {

  @Mock
  AuthorExistencePort authorExistence;

  @InjectMocks
  CreateAuthorIdentityService authorService;

  @Test
  void shouldRejectExistantAuthor() {
    var command = new CreateAuthorIdentityCommand(
        "John Doe",
        "johndoe123");
    when(authorExistence.existsByHandle(command.handle()))
        .thenReturn(true);

    assertThrows(
        DuplicateAuthorException.class,
        () -> {
          authorService.createIdentity(command);
        });

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

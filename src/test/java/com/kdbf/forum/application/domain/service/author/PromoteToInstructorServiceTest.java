package com.kdbf.forum.application.domain.service.author;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kdbf.forum.application.commons.Result;
import com.kdbf.forum.application.domain.model.entity.Author;
import com.kdbf.forum.application.domain.model.entity.UserRoles;
import com.kdbf.forum.application.port.in.author.command.PromoteToInstructorCommand;
import com.kdbf.forum.application.port.out.author.AuthorUpdatePort;
import com.kdbf.forum.application.port.out.author.FindAuthorsPort;
import com.kdbf.forum.mother.AuthorMother;

@Tag("service")
@ExtendWith(MockitoExtension.class)
public class PromoteToInstructorServiceTest {

  @Mock
  private FindAuthorsPort findAuthors;

  @Mock
  private AuthorUpdatePort updateAuthor;

  @InjectMocks
  private PromoteToInstructorService promoteService;

  @Test
  void shouldChangeRoleToInstructor() {
    Author author = spy(AuthorMother.sample());
    when(findAuthors.findByHandle(anyString()))
        .thenReturn(Optional.of(author));

    ArgumentCaptor<Author> captor = ArgumentCaptor.forClass(Author.class);

    promoteService.promoteToInstructor(new PromoteToInstructorCommand(author.getHandle()));

    verify(author).promoteToInstructor();
    verify(updateAuthor).updateAuthor(captor.capture());
    Author passedAuthor = captor.getValue();
    assertEquals(UserRoles.ROLE_INSTRUCTOR, passedAuthor.getRole());

  }

  @Test
  void shouldReturnNullIfAuthorDoesntExist() {
    when(findAuthors.findByHandle(anyString()))
        .thenReturn(Optional.empty());

    var command = new PromoteToInstructorCommand("handle-user");

    Result<Author> result = promoteService.promoteToInstructor(command);

    assertFalse(result.success());
    assertNull(result.value());
    verify(updateAuthor, never()).updateAuthor(any(Author.class));

  }

  @Test
  void reportWetherTheAuthorIsAnInstructorAlready() {
    Author author = AuthorMother.sample();
    author.promoteToInstructor();

    var command = new PromoteToInstructorCommand(author.getHandle());

    when(findAuthors.findByHandle(anyString()))
        .thenReturn(Optional.of(author));

    Result<Author> authorResult = promoteService.promoteToInstructor(command);

    assertFalse(authorResult.success());
    assertNotNull(authorResult.value());
    assertEquals(authorResult.value().getHandle() +
        " is already an Instructor", authorResult.message());

  }

}

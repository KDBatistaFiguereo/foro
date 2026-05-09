package com.kdbf.forum.application.domain.service.author;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kdbf.forum.application.commons.Result;
import com.kdbf.forum.application.domain.model.entity.Author;
import com.kdbf.forum.application.domain.model.entity.UserRoles;
import com.kdbf.forum.application.port.in.author.PromoteToInstructorUseCase;
import com.kdbf.forum.application.port.in.author.command.PromoteToInstructorCommand;
import com.kdbf.forum.application.port.out.author.AuthorUpdatePort;
import com.kdbf.forum.application.port.out.author.FindAuthorsPort;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PromoteToInstructorService implements
    PromoteToInstructorUseCase {

  private final FindAuthorsPort findAuthors;
  private final AuthorUpdatePort updateAuthor;

  @Override
  public Result<Author> promoteToInstructor(PromoteToInstructorCommand command) {
    Optional<Author> regularAuthor = findAuthors.findByHandle(command.handle());

    if (regularAuthor.isEmpty()) {
      return new Result<Author>(
          false,
          null,
          "The author " + command.handle() + "does not exist");
    } else if (regularAuthor.get().getRole() == UserRoles.ROLE_INSTRUCTOR) {
      return new Result<Author>(
          false,
          regularAuthor.get(),
          command.handle() + " is already an Instructor");
    }

    regularAuthor.get().promoteToInstructor();
    Author payload = updateAuthor.updateAuthor(regularAuthor.get());

    return new Result<Author>(
        true,
        payload,
        command.handle() + " promoted succesfully.");
  }

}

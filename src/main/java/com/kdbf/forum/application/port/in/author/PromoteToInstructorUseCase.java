package com.kdbf.forum.application.port.in.author;

import com.kdbf.forum.application.commons.Result;
import com.kdbf.forum.application.domain.model.entity.Author;
import com.kdbf.forum.application.port.in.author.command.PromoteToInstructorCommand;

public interface PromoteToInstructorUseCase {
  Result<Author> promoteToInstructor(PromoteToInstructorCommand command);
}

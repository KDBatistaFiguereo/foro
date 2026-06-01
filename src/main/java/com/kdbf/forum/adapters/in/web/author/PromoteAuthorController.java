package com.kdbf.forum.adapters.in.web.author;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kdbf.forum.adapters.in.web.author.dto.AuthorResponseDto;
import com.kdbf.forum.application.commons.Result;
import com.kdbf.forum.application.domain.model.entity.Author;
import com.kdbf.forum.application.port.in.author.PromoteToInstructorUseCase;
import com.kdbf.forum.application.port.in.author.command.PromoteToInstructorCommand;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class PromoteAuthorController {

  private PromoteToInstructorUseCase promoteAuthor;

  @PostMapping("/authors/{handle}/promote")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<AuthorResponseDto> promoteToInstructor(
      @PathVariable("handle") String handle) {

    var command = new PromoteToInstructorCommand(handle);
    Result<Author> promoted = promoteAuthor.promoteToInstructor(command);

    AuthorResponseDto response = new AuthorResponseDto(
        promoted.success(),
        promoted.message());

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(response);
  }
}

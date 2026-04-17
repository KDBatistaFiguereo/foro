package com.kdbf.forum.application.port.out;

import com.kdbf.forum.application.domain.model.entity.Author;

public interface AuthorRegistrationPort {
  void registerAuthor(Author author, String email, String hashedPassword);
}

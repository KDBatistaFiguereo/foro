package com.kdbf.forum.application.port.out.author;

import com.kdbf.forum.application.domain.model.entity.Author;

public interface AuthorUpdatePort {
  Author updateAuthor(Author author);
}

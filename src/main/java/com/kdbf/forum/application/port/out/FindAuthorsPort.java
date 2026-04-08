package com.kdbf.forum.application.port.out;

import java.util.Optional;

import com.kdbf.forum.application.domain.model.entity.Author;

public interface FindAuthorsPort {
  Optional<Author> findByHandle(String handle);
}

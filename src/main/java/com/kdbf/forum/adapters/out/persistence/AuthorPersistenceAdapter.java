package com.kdbf.forum.adapters.out.persistence;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.kdbf.forum.adapters.out.persistence.entity.AuthorJpa;
import com.kdbf.forum.adapters.out.persistence.mapper.AuthorJpaMapper;
import com.kdbf.forum.adapters.out.persistence.repository.AuthorRepository;
import com.kdbf.forum.adapters.out.persistence.utility.CycleAvoidingMappingContext;
import com.kdbf.forum.application.domain.model.entity.Author;
import com.kdbf.forum.application.domain.service.exception.DuplicateAuthorException;
import com.kdbf.forum.application.port.out.AuthorExistencePort;
import com.kdbf.forum.application.port.out.AuthorRegistrationPort;
import com.kdbf.forum.application.port.out.FindAuthorsPort;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AuthorPersistenceAdapter implements
    FindAuthorsPort, AuthorExistencePort, AuthorRegistrationPort {

  private final AuthorJpaMapper authorMapper;
  private final CycleAvoidingMappingContext context;
  private final AuthorRepository authorRepository;

  @Override
  public Optional<Author> findByHandle(String handle) {
    return authorRepository.findByHandle(handle)
        .map(x -> authorMapper.toDomain(x, context));
  }

  @Override
  public boolean existsByHandle(String handle) {
    return authorRepository.existsByHandle(handle);
  }

  @Override
  public void registerAuthor(Author author, String email, String hashedPassword) {
    if (existsByHandle(author.getHandle())) {
      throw new DuplicateAuthorException("An author already exists by this handle");
    }

    AuthorJpa authorJpa = authorMapper.toJpa(author, context, email, hashedPassword);

    authorRepository.save(authorJpa);

  }

}

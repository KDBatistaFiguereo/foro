package com.kdbf.forum.adapters.out.persistence.author;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.kdbf.forum.adapters.out.persistence.author.mapper.AuthorJpaMapper;
import com.kdbf.forum.adapters.out.persistence.utility.CycleAvoidingMappingContext;
import com.kdbf.forum.application.domain.model.entity.Author;
import com.kdbf.forum.application.domain.model.exception.NonExistantAuthorException;
import com.kdbf.forum.application.domain.service.exception.DuplicateAuthorException;
import com.kdbf.forum.application.port.out.author.AuthorExistencePort;
import com.kdbf.forum.application.port.out.author.AuthorRegistrationPort;
import com.kdbf.forum.application.port.out.author.AuthorUpdatePort;
import com.kdbf.forum.application.port.out.author.FindAuthorsPort;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AuthorPersistenceAdapter implements
    FindAuthorsPort,
    AuthorExistencePort,
    AuthorRegistrationPort,
    AuthorUpdatePort {

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

  @Override
  public Author updateAuthor(Author author) {
    return authorRepository.findByHandle(author.getHandle())
        .map(existingAuthor -> {
          authorMapper.updateJpaFromDomain(author, existingAuthor, context);
          AuthorJpa updated = authorRepository.save(existingAuthor);
          return authorMapper.toDomain(updated, context);
        }).orElseThrow(
            () -> new NonExistantAuthorException("The author does not exist"));
  }

}

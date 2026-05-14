package com.kdbf.forum.adapters.out.persistence.author;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorJpa, Long> {

  public Optional<AuthorJpa> findByUsername(String username);

  public Optional<AuthorJpa> findByDisplayName(String displayName);

  public Optional<AuthorJpa> findByHandle(String handle);

  public boolean existsByUsername(String username);

  public int countByHandle(String handle);

  public boolean existsByDisplayName(String displayName);

  public boolean existsByHandle(String handle);
}

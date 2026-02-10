package com.kdbf.forum.adapters.out.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kdbf.forum.adapters.out.persistence.entity.AuthorJpa;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorJpa, Long> {
  @Query("SELECT a FROM AuthorJpa a WHERE a.username = :username")
  public Optional<AuthorJpa> byUserName(String username);

  @Query("""
      SELECT CASE
          WHEN COUNT(a) > 0 THEN true
          ELSE false
      END
      FROM AuthorJpa a
      WHERE a.username = :username
        """)
  public Boolean exists(String username);
}

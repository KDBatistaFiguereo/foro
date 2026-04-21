package com.kdbf.forum.application.port.out.author;

public interface AuthorExistencePort {
  boolean existsByHandle(String handle);
}

package com.kdbf.forum.application.port.out;

public interface AuthorExistencePort {
  boolean existsByHandle(String handle);
}

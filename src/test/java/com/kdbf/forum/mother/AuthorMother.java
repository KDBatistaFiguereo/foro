package com.kdbf.forum.mother;

import com.kdbf.forum.application.domain.model.entity.Author;

public class AuthorMother {
  public static Author sample() {
    return new Author("John Doe",
        "johndoe");
  }

  public static Author customSample(String displayName, String handle) {
    return new Author(displayName, handle);
  }

}

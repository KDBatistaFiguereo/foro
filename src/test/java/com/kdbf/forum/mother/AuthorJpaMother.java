package com.kdbf.forum.mother;

import com.kdbf.forum.adapters.out.persistence.author.AuthorJpa;
import com.kdbf.forum.application.domain.model.entity.UserRoles;

public class AuthorJpaMother {
  public static AuthorJpa sample() {
    return new AuthorJpa(
        "John doe",
        "johndoe",
        "JohnDoe@gmail.com",
        "e9werw9ejr9wef",
        UserRoles.ROLE_MEMBER);
  }

  public static AuthorJpa sampleWithEmail(String email) {
    return new AuthorJpa(
        "John Doe",
        "johndoe",
        email,
        "e9werw9ejr9wef",
        UserRoles.ROLE_MEMBER);
  }

  public static AuthorJpa sampleWithNameAndHandle(String name, String handle) {
    return new AuthorJpa(
        name,
        handle,
        "johnDoe@gmail.com",
        "e9werw9ejr9wef",
        UserRoles.ROLE_MEMBER);
  }

}

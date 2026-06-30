package com.kdbf.forum.application.domain.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;

@Tag("temp")
public class AuthorTest {

  public void shouldIncreaseFollowerCount() {

    final int FOLLOWER_COUNT = 10;
    Author author = new Author(
        "John",
        "littlejohn",
        UserRoles.ROLE_MEMBER,
        FOLLOWER_COUNT);

    author.increaseFollowers();

    assertEquals(11, author.getFollowerCount());
  }

}

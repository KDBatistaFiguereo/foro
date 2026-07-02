package com.kdbf.forum.application.domain.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("temp")
public class AuthorTest {

  Author author;

  @BeforeEach
  public void setUp() {
    author = new Author(
        "Mark Dan",
        "mrkdn12",
        UserRoles.ROLE_MEMBER);

  }

  @Test
  public void ShouldFollowAuthor() {

    author.followAuthor("sarag45");
    assertEquals(1, author.getFollowing().size());
    assertTrue(author.getFollowing().contains("sarag45"));

  }

  // public void shouldIncreaseFollowerCount() {
  //
  // final int FOLLOWER_COUNT = 10;
  // Author author = new Author(
  // "John",
  // "littlejohn",
  // UserRoles.ROLE_MEMBER,
  // FOLLOWER_COUNT);
  //
  // author.increaseFollowers();
  //
  // assertEquals(11, author.getFollowerCount());
  // }

}

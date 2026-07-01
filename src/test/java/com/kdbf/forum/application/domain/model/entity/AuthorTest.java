package com.kdbf.forum.application.domain.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Tag;

@Tag("temp")
public class AuthorTest {

  public void ShouldFollowAuthor() {
    Set<String> listAuthors = Set.of(
        "johndoe",
        "janedoe");

    Author author = new Author(
        "Mark Dan",
        "mrkdn12",
        UserRoles.ROLE_MEMBER,
        listAuthors);

    author.followAuthor("sarag45");

    assertEquals(3, author.getFollows().length());
    assertEquals("sarag45", actual);

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

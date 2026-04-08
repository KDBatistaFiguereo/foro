package com.kdbf.forum.application.domain.model.entity;

import com.kdbf.forum.application.domain.model.exception.InvalidDisplayNameException;

import lombok.Getter;

@Getter
public class Author {

  String displayName;
  String handle;

  public Author(String displayName, String handle) {
    if (!isValidName(displayName)) {
      throw new InvalidDisplayNameException("The name is not valid");
    }

    this.displayName = displayName;
    this.handle = handle;

  }

  private Boolean isValidName(String name) {
    final int MAX_LENGTH = 50;
    final int MIN_LENGTH = 3;
    Boolean flag = true;
    if (name == null) {
      flag = false;
    }
    if (name.isBlank()) {
      flag = false;
    }
    if (name.length() < MIN_LENGTH || name.length() > MAX_LENGTH) {
      flag = false;
    }
    return flag;
  }

}

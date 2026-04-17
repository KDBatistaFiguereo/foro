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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((displayName == null) ? 0 : displayName.hashCode());
    result = prime * result + ((handle == null) ? 0 : handle.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Author other = (Author) obj;
    if (displayName == null) {
      if (other.displayName != null)
        return false;
    } else if (!displayName.equals(other.displayName))
      return false;
    if (handle == null) {
      if (other.handle != null)
        return false;
    } else if (!handle.equals(other.handle))
      return false;
    return true;
  }

}

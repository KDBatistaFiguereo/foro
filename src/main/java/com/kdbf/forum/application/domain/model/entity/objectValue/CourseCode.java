package com.kdbf.forum.application.domain.model.entity.objectValue;

import com.kdbf.forum.application.domain.model.exception.InvalidCourseCodeException;

import jakarta.persistence.Embeddable;

@Embeddable
public record CourseCode(String code) {
  public CourseCode {
    if (code == null || code.isEmpty()) {
      throw new InvalidCourseCodeException("Course code can't be empty");
    }

    code = normalize(code);

    if (!validateLength(code)) {
      throw new InvalidCourseCodeException("A course code needs the exact length of 8");
    }

    if (!validateFormat(code)) {
      throw new InvalidCourseCodeException("Wrong format. good format example: MAT-0014");
    }

  }

  private Boolean validateLength(String input) {
    final int EXACT_LENGTH = 8;
    return input.length() == EXACT_LENGTH;
  }

  private String normalize(String input) {
    return input.toUpperCase().trim();
  }

  private Boolean validateFormat(String input) {
    // Three letters followed by a hyphen followed by 4 digits
    String regex = "^[A-Z]{3}-\\d{4}$";

    return input.matches(regex);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((code == null) ? 0 : code.hashCode());
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
    CourseCode other = (CourseCode) obj;
    if (code == null) {
      if (other.code != null)
        return false;
    } else if (!code.equals(other.code))
      return false;
    return true;
  }

}

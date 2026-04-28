package com.kdbf.forum.application.domain.model.entity;

import com.kdbf.forum.application.domain.model.entity.objectValue.CourseCode;

import lombok.Getter;

@Getter
public class Course {
  CourseCode courseCode;
  String courseName;

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((courseCode == null) ? 0 : courseCode.hashCode());
    result = prime * result + ((courseName == null) ? 0 : courseName.hashCode());
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
    Course other = (Course) obj;
    if (courseCode == null) {
      if (other.courseCode != null)
        return false;
    } else if (!courseCode.equals(other.courseCode))
      return false;
    if (courseName == null) {
      if (other.courseName != null)
        return false;
    } else if (!courseName.equals(other.courseName))
      return false;
    return true;
  }

  public Course(String courseName, CourseCode courseCode) {
    this.courseName = courseName;
    this.courseCode = courseCode;
  }

  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }

}

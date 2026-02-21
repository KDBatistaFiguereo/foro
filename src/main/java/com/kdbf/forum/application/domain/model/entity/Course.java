package com.kdbf.forum.application.domain.model.entity;

import com.kdbf.forum.application.domain.model.entity.objectValue.CourseCode;

import lombok.Getter;

@Getter
public class Course {
  CourseCode courseCode;
  String courseName;

  public Course(String courseName, CourseCode courseCode) {
    this.courseName = courseName;
    this.courseCode = courseCode;
  }

}

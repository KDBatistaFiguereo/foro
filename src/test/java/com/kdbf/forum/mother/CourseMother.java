package com.kdbf.forum.mother;

import com.kdbf.forum.application.domain.model.entity.Course;
import com.kdbf.forum.application.domain.model.entity.objectValue.CourseCode;

public class CourseMother {
  public static Course sample() {
    return new Course(
        "Example course",
        new CourseCode("EXA-0001"));
  }

  public static Course customSample(
      String courseName, String courseCode) {
    return new Course(courseName, new CourseCode(courseCode));
  }

}

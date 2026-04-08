package com.kdbf.forum.mother;

import com.kdbf.forum.adapters.out.persistence.entity.CourseJpa;
import com.kdbf.forum.application.domain.model.entity.objectValue.CourseCode;

public class CourseJpaMother {

  public static CourseJpa sample() {
    return new CourseJpa("Example course",
        new CourseCode("EXA-0001"));
  }

  public static CourseJpa customSample(String courseName, String courseCode) {
    return new CourseJpa(
        courseName,
        new CourseCode(courseCode));
  }

}

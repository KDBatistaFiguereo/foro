package com.kdbf.forum.application.port.out;

import java.util.List;
import java.util.Optional;

import com.kdbf.forum.application.domain.model.entity.Course;
import com.kdbf.forum.application.domain.model.entity.objectValue.CourseCode;

public interface FindCoursesPort {
  List<Course> findAll();

  Optional<Course> findByCode(CourseCode courseCode);
}

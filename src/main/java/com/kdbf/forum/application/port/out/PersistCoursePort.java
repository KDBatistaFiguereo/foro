package com.kdbf.forum.application.port.out;

import com.kdbf.forum.application.domain.model.entity.Course;

public interface PersistCoursePort {
  Course persistCourse(Course course);
}

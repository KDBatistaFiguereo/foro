package com.kdbf.forum.application.port.out;

import java.util.UUID;

import com.kdbf.forum.application.domain.model.entity.objectValue.CourseCode;

public interface TopicsExistencePort {
  boolean existsByTitleAndCourseName(String title, String courseName);

  boolean existsByTitleAndCourseCode(String title, CourseCode courseCode);

  boolean existsByPublicId(UUID publicId);
}

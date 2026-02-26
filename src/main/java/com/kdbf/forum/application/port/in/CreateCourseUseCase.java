package com.kdbf.forum.application.port.in;

import com.kdbf.forum.application.domain.model.entity.Course;

//TODO separate command and queries into folders
public interface CreateCourseUseCase {
  Course createCourse(CreateCourseCommand command);
}

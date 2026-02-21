package com.kdbf.forum.application.port.in;

import com.kdbf.forum.application.domain.model.entity.Course;

public interface CreateCourseUseCase {

  Course createCourse(CreateCourseCommand command);
}

package com.kdbf.forum.application.port.in.course;

import com.kdbf.forum.application.domain.model.entity.Course;
import com.kdbf.forum.application.port.in.course.command.CreateCourseCommand;

//TODO separate command and queries into folders
public interface CreateCourseUseCase {
  Course createCourse(CreateCourseCommand command);
}

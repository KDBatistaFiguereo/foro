package com.kdbf.forum.application.port.in.course;

import com.kdbf.forum.application.domain.model.entity.Course;
import com.kdbf.forum.application.port.in.course.command.UpdateCourseCommand;

public interface UpdateCourseUseCase {
  Course updateCourseName(UpdateCourseCommand command);
}

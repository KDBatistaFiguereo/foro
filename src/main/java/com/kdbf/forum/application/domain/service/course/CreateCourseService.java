package com.kdbf.forum.application.domain.service.course;

import org.springframework.stereotype.Service;

import com.kdbf.forum.application.domain.model.entity.Course;
import com.kdbf.forum.application.domain.model.entity.objectValue.CourseCode;
import com.kdbf.forum.application.domain.model.exception.DuplicateCourseException;
import com.kdbf.forum.application.port.in.course.CreateCourseUseCase;
import com.kdbf.forum.application.port.in.course.command.CreateCourseCommand;
import com.kdbf.forum.application.port.out.course.CourseExistencePort;
import com.kdbf.forum.application.port.out.course.PersistCoursePort;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CreateCourseService implements CreateCourseUseCase {
  private final PersistCoursePort persistCourse;
  private final CourseExistencePort courseExistence;

  @Override
  public Course createCourse(CreateCourseCommand command) {

    if (courseExistence.existsByCode(command.courseCode())) {
      throw new DuplicateCourseException(
          String.format("The course with the code %s already exsits", command.courseCode()));
    }

    Course course = new Course(command.courseName(),
        new CourseCode(command.courseCode()));

    return persistCourse.persistCourse(course);

  }

}

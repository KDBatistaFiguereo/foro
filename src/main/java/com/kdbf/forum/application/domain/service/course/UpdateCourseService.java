package com.kdbf.forum.application.domain.service.course;

import org.springframework.stereotype.Service;

import com.kdbf.forum.application.domain.model.entity.Course;
import com.kdbf.forum.application.domain.model.entity.objectValue.CourseCode;
import com.kdbf.forum.application.domain.model.exception.CourseNotFoundException;
import com.kdbf.forum.application.port.in.course.UpdateCourseUseCase;
import com.kdbf.forum.application.port.in.course.command.UpdateCourseCommand;
import com.kdbf.forum.application.port.out.course.FindCoursesPort;
import com.kdbf.forum.application.port.out.course.PersistCoursePort;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UpdateCourseService implements UpdateCourseUseCase {

  FindCoursesPort findCourses;

  PersistCoursePort persistCourse;

  @Override
  public Course updateCourseName(UpdateCourseCommand command) {
    CourseCode courseCode = new CourseCode(command.courseCode());
    Course oldCourse = findCourses.findByCode(courseCode)
        .orElseThrow(() -> new CourseNotFoundException("The course does not exist"));
    oldCourse.setCourseName(command.newCourseName());

    return persistCourse.persistCourse(oldCourse);
  }

}

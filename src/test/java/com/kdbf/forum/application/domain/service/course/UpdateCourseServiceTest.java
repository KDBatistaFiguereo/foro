package com.kdbf.forum.application.domain.service.course;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kdbf.forum.application.domain.model.entity.Course;
import com.kdbf.forum.application.domain.model.entity.objectValue.CourseCode;
import com.kdbf.forum.application.domain.model.exception.CourseNotFoundException;
import com.kdbf.forum.application.port.in.course.command.UpdateCourseCommand;
import com.kdbf.forum.application.port.out.course.FindCoursesPort;
import com.kdbf.forum.application.port.out.course.PersistCoursePort;
import static org.mockito.Mockito.any;

@Tag("service")
@ExtendWith(MockitoExtension.class)
public class UpdateCourseServiceTest {

  @Mock
  PersistCoursePort persistCourse;

  @Mock
  FindCoursesPort findCourses;

  @InjectMocks
  UpdateCourseService updateCourse;

  @Test
  void shouldReturnUpdatedTopic() {
    UpdateCourseCommand command = new UpdateCourseCommand(
        "EXA-0001",
        "New title");

    Course existingCourse = new Course(
        "Old title",
        new CourseCode("EXA-0001"));

    when(findCourses.findByCode(any()))
        .thenReturn(Optional.of(existingCourse));

    updateCourse.updateCourseName(command);

    verify(persistCourse).persistCourse(
        eq(new Course(
            "New title",
            new CourseCode("EXA-0001"))));

  }

  @Test
  void shouldFailNonExistantCourse() {
    UpdateCourseCommand command = new UpdateCourseCommand(
        "EXA-0001",
        "New title");
    CourseCode courseCode = new CourseCode(command.courseCode());

    when(findCourses.findByCode(courseCode))
        .thenReturn(Optional.empty());

    assertThrows(CourseNotFoundException.class, () -> {
      updateCourse.updateCourseName(command);
    });
  }

}

package com.kdbf.forum.adapters.in.web.course;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kdbf.forum.adapters.in.web.course.dto.CourseDto;
import com.kdbf.forum.adapters.in.web.course.mapper.CourseDtoMapper;
import com.kdbf.forum.application.domain.model.entity.Course;
import com.kdbf.forum.application.port.in.course.CreateCourseUseCase;
import com.kdbf.forum.application.port.in.course.command.CreateCourseCommand;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class CreateCourseController {

  private final CreateCourseUseCase createCourse;
  private final CourseDtoMapper courseMapper;

  @PostMapping("/courses")
  @PreAuthorize("hasRole('INSTRUCTOR')")
  public ResponseEntity<CourseDto> registerCourse(
      @RequestBody @Valid CourseDto courseDto) {

    CreateCourseCommand command = new CreateCourseCommand(
        courseDto.courseCode(),
        courseDto.courseName());
    Course savedCourse = createCourse.createCourse(command);

    CourseDto response = courseMapper.toDto(savedCourse);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(response);
  }

}

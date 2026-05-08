package com.kdbf.forum.adapters.in.web.course;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kdbf.forum.adapters.in.web.course.dto.CourseDto;
import com.kdbf.forum.adapters.in.web.course.dto.UpdateCourseDto;
import com.kdbf.forum.adapters.in.web.course.mapper.CourseDtoMapper;
import com.kdbf.forum.application.domain.model.entity.Course;
import com.kdbf.forum.application.port.in.course.UpdateCourseUseCase;
import com.kdbf.forum.application.port.in.course.command.UpdateCourseCommand;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class UpdateCourseController {

  private UpdateCourseUseCase updateCourse;
  private CourseDtoMapper courseMapper;

  @PutMapping("/courses/{courseCode}")
  public ResponseEntity<CourseDto> updateCourseName(
      @PathVariable("courseCode") String courseCode,
      @RequestBody @Valid UpdateCourseDto updateDto) {

    UpdateCourseCommand command = new UpdateCourseCommand(
        courseCode,
        updateDto.newName());
    Course updatedCourse = updateCourse.updateCourseName(command);

    CourseDto response = courseMapper.toDto(updatedCourse);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(response);

  }
}

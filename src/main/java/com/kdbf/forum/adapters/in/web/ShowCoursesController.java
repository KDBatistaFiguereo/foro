package com.kdbf.forum.adapters.in.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kdbf.forum.adapters.in.web.dto.CourseDto;
import com.kdbf.forum.adapters.in.web.mapper.CourseDtoMapper;
import com.kdbf.forum.application.port.in.FindCoursesUseCase;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ShowCoursesController {

  private final FindCoursesUseCase findCourses;
  private final CourseDtoMapper courseMapper;

  @GetMapping("/courses")
  public ResponseEntity<List<CourseDto>> showAllCourses() {
    List<CourseDto> courses;
    courses = findCourses.findAll().stream()
        .map(x -> courseMapper.toDto(x))
        .toList();

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(courses);
  }

}

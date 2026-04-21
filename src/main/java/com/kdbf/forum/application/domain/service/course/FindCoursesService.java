package com.kdbf.forum.application.domain.service.course;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kdbf.forum.application.domain.model.entity.Course;
import com.kdbf.forum.application.port.in.course.FindCoursesUseCase;
import com.kdbf.forum.application.port.out.course.FindCoursesPort;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FindCoursesService implements FindCoursesUseCase {

  private final FindCoursesPort findCourses;

  @Override
  public List<Course> findAll() {
    return findCourses.findAll();
  }
}

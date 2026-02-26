package com.kdbf.forum.application.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kdbf.forum.application.domain.model.entity.Course;
import com.kdbf.forum.application.port.in.FindCoursesUseCase;
import com.kdbf.forum.application.port.out.FindCoursesPort;

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

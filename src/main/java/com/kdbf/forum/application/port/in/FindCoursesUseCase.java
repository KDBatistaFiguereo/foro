package com.kdbf.forum.application.port.in;

import java.util.List;

import com.kdbf.forum.application.domain.model.entity.Course;

public interface FindCoursesUseCase {
  List<Course> findAll();

}

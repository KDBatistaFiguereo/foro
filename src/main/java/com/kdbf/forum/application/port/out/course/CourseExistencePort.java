package com.kdbf.forum.application.port.out.course;

public interface CourseExistencePort {
  Boolean existsByCode(String code);
}

package com.kdbf.forum.application.port.out;

public interface TopicsExistencePort {
  Boolean existsByTitleAndCourseName(String title, String courseName);
}

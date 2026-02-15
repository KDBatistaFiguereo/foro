package com.kdbf.forum.application.port.out;

import java.util.UUID;

public interface TopicsExistencePort {
  Boolean existsByTitleAndCourseName(String title, String courseName);

  Boolean existsByPublicId(UUID publicId);
}

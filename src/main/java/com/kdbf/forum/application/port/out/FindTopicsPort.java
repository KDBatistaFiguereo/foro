package com.kdbf.forum.application.port.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.kdbf.forum.application.domain.model.entity.Topic;

public interface FindTopicsPort {
  List<Topic> findAll();

  Optional<Topic> byPublicId(UUID publicId);
}

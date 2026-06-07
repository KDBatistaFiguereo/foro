package com.kdbf.forum.application.port.out.topic;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;

import com.kdbf.forum.application.domain.model.entity.Topic;

public interface FindTopicsPort {
  List<Topic> findAll();

  List<Topic> findAllByTitle(String title, Pageable pageable);

  Optional<Topic> byPublicId(UUID publicId);
}

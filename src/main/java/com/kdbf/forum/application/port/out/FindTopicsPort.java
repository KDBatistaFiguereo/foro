package com.kdbf.forum.application.port.out;

import java.util.List;

import com.kdbf.forum.application.domain.model.entity.Topic;

public interface FindTopicsPort {
  List<Topic> findAll();
}

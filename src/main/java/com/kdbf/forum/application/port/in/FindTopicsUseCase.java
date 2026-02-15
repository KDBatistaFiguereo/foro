package com.kdbf.forum.application.port.in;

import java.util.List;
import com.kdbf.forum.application.domain.model.entity.Topic;

public interface FindTopicsUseCase {
  List<Topic> findAllTopics();

  Topic findTopicById(FindTopicByIdQuery query);
}

package com.kdbf.forum.application.port.in.topic;

import java.util.List;
import com.kdbf.forum.application.domain.model.entity.Topic;
import com.kdbf.forum.application.port.in.topic.query.FindTopicByIdQuery;

public interface FindTopicsUseCase {
  List<Topic> findAllTopics();

  Topic findTopicById(FindTopicByIdQuery query);
}

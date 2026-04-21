package com.kdbf.forum.application.domain.service.topic;

import java.util.List;
import org.springframework.stereotype.Service;

import com.kdbf.forum.application.domain.model.entity.Topic;
import com.kdbf.forum.application.domain.model.exception.TopicNotFoundException;
import com.kdbf.forum.application.port.in.topic.FindTopicsUseCase;
import com.kdbf.forum.application.port.in.topic.query.FindTopicByIdQuery;
import com.kdbf.forum.application.port.out.topic.FindTopicsPort;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class FindTopicsService implements FindTopicsUseCase {

  private final FindTopicsPort findTopicsPort;

  @Override
  public List<Topic> findAllTopics() {
    return findTopicsPort.findAll();
  }

  @Override
  public Topic findTopicById(FindTopicByIdQuery query) {
    return findTopicsPort.byPublicId(query.publicId())
        .orElseThrow(() -> new TopicNotFoundException("The topic does not exist"));
  }
}

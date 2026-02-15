package com.kdbf.forum.application.domain.service;

import org.springframework.stereotype.Service;

import com.kdbf.forum.application.domain.model.entity.Topic;
import com.kdbf.forum.application.domain.model.exception.TopicNotFoundException;
import com.kdbf.forum.application.port.in.UpdateTopicCommand;
import com.kdbf.forum.application.port.in.UpdateTopicUseCase;
import com.kdbf.forum.application.port.out.FindTopicsPort;
import com.kdbf.forum.application.port.out.PersistTopicsPort;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UpdateTopicService implements UpdateTopicUseCase {

  private final PersistTopicsPort topicPersist;
  private final FindTopicsPort findTopics;

  @Override
  public Topic updateTopic(UpdateTopicCommand command) {
    Topic oldTopic = findTopics.byPublicId(command.publicId())
        .orElseThrow(() -> new TopicNotFoundException("cant update nonexistant Topic"));

    Topic updatedTopic = Topic.reconstitute(
        oldTopic.getCourse(),
        oldTopic.getPublicId(),
        command.title(),
        command.body(),
        oldTopic.getAuthor(),
        oldTopic.getCreationDate(),
        oldTopic.getStatus());
    return topicPersist.persistTopic(updatedTopic);

  }

}

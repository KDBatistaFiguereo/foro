package com.kdbf.forum.application.domain.service.topic;

import org.springframework.stereotype.Service;

import com.kdbf.forum.application.domain.model.entity.Topic;
import com.kdbf.forum.application.domain.model.exception.TopicNotFoundException;
import com.kdbf.forum.application.port.in.topic.DeleteTopicUseCase;
import com.kdbf.forum.application.port.in.topic.command.DeleteTopicCommand;
import com.kdbf.forum.application.port.out.topic.FindTopicsPort;
import com.kdbf.forum.application.port.out.topic.PersistTopicsPort;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class DeleteTopicService implements DeleteTopicUseCase {

  private final FindTopicsPort findTopics;

  private final PersistTopicsPort persistTopics;

  @Transactional
  @Override
  // soft delete
  public void deleteTopic(DeleteTopicCommand command) {

    Topic topicToDelete = findTopics.byPublicId(command.publicId())
        .orElseThrow(() -> new TopicNotFoundException("Cant delete a nonexistant topic"));

    topicToDelete.delete();

    persistTopics.persistTopic(topicToDelete);

  }

}

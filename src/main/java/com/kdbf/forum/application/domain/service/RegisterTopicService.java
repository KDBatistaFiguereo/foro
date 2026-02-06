package com.kdbf.forum.application.domain.service;

import org.springframework.stereotype.Service;

import com.kdbf.forum.application.domain.model.entity.Author;
import com.kdbf.forum.application.domain.model.entity.Course;
import com.kdbf.forum.application.domain.model.entity.Topic;
import com.kdbf.forum.application.port.in.RegisterTopicCommand;
import com.kdbf.forum.application.port.in.RegisterTopicUseCase;
import com.kdbf.forum.application.port.out.PersistTopicsPort;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegisterTopicService implements RegisterTopicUseCase {

  private final PersistTopicsPort persistTopic;

  @Override
  public Topic registerTopic(RegisterTopicCommand command) {
    Topic topic = Topic.newInstance(
        new Course(command.course()),
        command.title(),
        command.body(),
        new Author(command.author()));
    return persistTopic.persistTopic(topic);

  }

}

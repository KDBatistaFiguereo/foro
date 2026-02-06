package com.kdbf.forum.application.domain.service;

import org.springframework.stereotype.Service;

import com.kdbf.forum.application.domain.model.entity.Author;
import com.kdbf.forum.application.domain.model.entity.Course;
import com.kdbf.forum.application.domain.model.entity.Topic;
import com.kdbf.forum.application.domain.model.exception.DuplicateTopicException;
import com.kdbf.forum.application.port.in.RegisterTopicCommand;
import com.kdbf.forum.application.port.in.RegisterTopicUseCase;
import com.kdbf.forum.application.port.out.PersistTopicsPort;
import com.kdbf.forum.application.port.out.TopicsExistencePort;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegisterTopicService implements RegisterTopicUseCase {

  private final PersistTopicsPort persistTopic;
  private final TopicsExistencePort existencePort;

  @Override
  public Topic registerTopic(RegisterTopicCommand command) {
    if (existencePort.existsByTitleAndCourseName(command.title(), command.course())) {
      throw new DuplicateTopicException("A topic with this title exists in this course");
    }

    Topic topic = Topic.newInstance(
        new Course(command.course()),
        command.title(),
        command.body(),
        new Author(command.author()));

    return persistTopic.persistTopic(topic);

  }

}

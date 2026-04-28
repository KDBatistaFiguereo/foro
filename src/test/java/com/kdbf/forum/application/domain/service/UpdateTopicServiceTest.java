package com.kdbf.forum.application.domain.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kdbf.forum.application.domain.model.exception.TopicNotFoundException;
import com.kdbf.forum.application.domain.service.topic.UpdateTopicService;
import com.kdbf.forum.application.port.in.topic.command.UpdateTopicCommand;
import com.kdbf.forum.application.port.out.topic.FindTopicsPort;
import com.kdbf.forum.application.port.out.topic.PersistTopicsPort;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateTopicServiceTest {

  @Mock
  PersistTopicsPort persistTopics;

  @Mock
  FindTopicsPort findTopics;

  @InjectMocks
  UpdateTopicService updateService;

  @Test
  void shouldThrowExceptionIfTopicDoesntExist() {
    UpdateTopicCommand command = new UpdateTopicCommand(
        UUID.randomUUID(),
        "New title",
        "New body");

    when(findTopics.byPublicId(command.publicId()))
        .thenReturn(Optional.empty());

    assertThrows(TopicNotFoundException.class, () -> updateService.updateTopic(command));
  }
}

package com.kdbf.forum.application.domain.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import com.kdbf.forum.application.domain.model.exception.DuplicateTopicException;
import com.kdbf.forum.application.port.in.RegisterTopicCommand;
import com.kdbf.forum.application.port.out.PersistTopicsPort;
import com.kdbf.forum.application.port.out.TopicsExistencePort;

@ExtendWith(MockitoExtension.class)
public class RegisterTopicServiceTest {

  @Mock
  PersistTopicsPort persistPort;

  @Mock
  TopicsExistencePort topicExistence;

  @InjectMocks
  RegisterTopicService registerService;

  @Test
  public void shouldThrowExceptionIfTopicExists() {

    RegisterTopicCommand command = new RegisterTopicCommand("Duplicate title",
        "Hello",
        "duplicate_enjoyer",
        "CS-014");

    when(topicExistence.existsByTitleAndCourseName(
        command.title(),
        command.course()))
        .thenReturn(true);
    assertThrows(DuplicateTopicException.class, () -> {
      registerService.registerTopic(command);
    });

    verify(persistPort, never()).persistTopic(any());

  }
}

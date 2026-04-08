package com.kdbf.forum.application.domain.service;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import static org.mockito.Mockito.any;

import com.kdbf.forum.application.domain.model.entity.objectValue.CourseCode;
import com.kdbf.forum.application.domain.model.exception.DuplicateTopicException;
import com.kdbf.forum.application.port.in.RegisterTopicCommand;
import com.kdbf.forum.application.port.out.FindAuthorsPort;
import com.kdbf.forum.application.port.out.FindCoursesPort;
import com.kdbf.forum.application.port.out.PersistTopicsPort;
import com.kdbf.forum.application.port.out.TopicsExistencePort;
import com.kdbf.forum.mother.AuthorMother;
import com.kdbf.forum.mother.CourseMother;

import static org.mockito.ArgumentMatchers.anyString;

@Tag("temp")
@ExtendWith(MockitoExtension.class)
public class RegisterTopicServiceTest {

  @Mock
  PersistTopicsPort persistPort;

  @Mock
  TopicsExistencePort topicExistence;

  @Mock
  FindAuthorsPort findAuthors;

  @Mock
  FindCoursesPort findCourses;

  @InjectMocks
  RegisterTopicService registerService;

  @Test
  public void shouldThrowExceptionIfTopicExists() {

    // when(findAuthors.findByHandle(anyString())
    // .thenReturn(Optional.of(AuthorMother.sample())));

    when(findAuthors.findByHandle(anyString()))
        .thenReturn(Optional.of(AuthorMother.sample()));

    when(findCourses.findByCode(any(CourseCode.class)))
        .thenReturn(Optional.of(CourseMother.sample()));

    RegisterTopicCommand command = new RegisterTopicCommand(
        "Duplicate title",
        "Hello",
        AuthorMother.sample().getHandle(),
        CourseMother.sample().getCourseCode().code());

    when(topicExistence.existsByTitleAndCourseCode(
        command.title(),
        CourseMother.sample().getCourseCode()))
        .thenReturn(true);

    assertThrows(DuplicateTopicException.class, () -> {
      registerService.registerTopic(command);
    });

    verify(persistPort, never()).persistTopic(any());

  }
}

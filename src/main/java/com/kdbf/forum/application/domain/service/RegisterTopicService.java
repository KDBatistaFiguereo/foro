package com.kdbf.forum.application.domain.service;

import org.springframework.stereotype.Service;

import com.kdbf.forum.application.domain.model.entity.Author;
import com.kdbf.forum.application.domain.model.entity.Course;
import com.kdbf.forum.application.domain.model.entity.Topic;
import com.kdbf.forum.application.domain.model.entity.objectValue.CourseCode;
import com.kdbf.forum.application.domain.model.exception.AuthorNotFoundException;
import com.kdbf.forum.application.domain.model.exception.CourseNotFoundException;
import com.kdbf.forum.application.domain.model.exception.DuplicateTopicException;
import com.kdbf.forum.application.port.in.RegisterTopicCommand;
import com.kdbf.forum.application.port.in.RegisterTopicUseCase;
import com.kdbf.forum.application.port.out.FindAuthorsPort;
import com.kdbf.forum.application.port.out.FindCoursesPort;
import com.kdbf.forum.application.port.out.PersistTopicsPort;
import com.kdbf.forum.application.port.out.TopicsExistencePort;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegisterTopicService implements RegisterTopicUseCase {

  private final PersistTopicsPort persistTopic;
  private final TopicsExistencePort topicExistence;

  private final FindAuthorsPort findAuthors;

  private final FindCoursesPort findCourses;

  @Override
  public Topic registerTopic(RegisterTopicCommand command) {
    Author author = findAuthors.findByHandle(command.authorHandle())
        .orElseThrow((() -> new AuthorNotFoundException("There's no author with this handle")));
    Course course = findCourses.findByCode(new CourseCode(command.courseCode()))
        .orElseThrow(() -> new CourseNotFoundException("The course does not exist"));

    if (topicExistence.existsByTitleAndCourseCode(command.title(), course.getCourseCode())) {
      throw new DuplicateTopicException("A topic with this title exists in this course");
    }

    Topic topic = Topic.newInstance(
        course,
        command.title(),
        command.body(),
        author);

    return persistTopic.persistTopic(topic);

  }

}

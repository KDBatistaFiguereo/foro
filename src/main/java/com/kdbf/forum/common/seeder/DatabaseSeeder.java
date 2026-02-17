package com.kdbf.forum.common.seeder;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.kdbf.forum.adapters.out.persistence.entity.AuthorJpa;
import com.kdbf.forum.adapters.out.persistence.entity.CourseJpa;
import com.kdbf.forum.adapters.out.persistence.entity.TopicJpa;
import com.kdbf.forum.adapters.out.persistence.repository.AuthorRepository;
import com.kdbf.forum.adapters.out.persistence.repository.CourseRepository;
import com.kdbf.forum.adapters.out.persistence.repository.TopicRepository;
import com.kdbf.forum.application.domain.model.entity.objectValue.TopicStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DatabaseSeeder implements CommandLineRunner {

  @Autowired
  private AuthorRepository authorRepository;

  @Autowired
  private CourseRepository courseRepository;

  @Autowired
  private TopicRepository topicRepository;

  @Override
  public void run(String... args) throws Exception {
    AuthorJpa authorJpa;
    CourseJpa courseJpa;
    if (authorRepository.count() == 0) {
      authorJpa = new AuthorJpa("Anonymous");
      authorRepository.save(authorJpa);
      log.info("Author {} created", authorJpa.getUsername());
    } else {
      authorJpa = authorRepository.findAll().get(0);
    }

    if (courseRepository.count() == 0) {
      courseJpa = new CourseJpa("Programming fundamentals");
      courseRepository.save(courseJpa);
      log.info("Course {} created", courseJpa.getCourseName());
    } else {
      courseJpa = courseRepository.findAll().get(0);
    }

    if (topicRepository.count() == 0) {
      TopicJpa topicJpa1 = new TopicJpa(
          UUID.randomUUID(),
          "Forum app",
          "A topic needs an existing author and course, such as mine",
          authorJpa,
          courseJpa,
          TopicStatus.DRAFT,
          LocalDateTime.now());
      topicRepository.save(topicJpa1);
    }
  }

}

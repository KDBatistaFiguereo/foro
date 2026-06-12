package com.kdbf.forum.adapters.web.mother;

import java.time.LocalDateTime;
import java.util.UUID;

import com.kdbf.forum.application.domain.model.entity.Author;
import com.kdbf.forum.application.domain.model.entity.Course;
import com.kdbf.forum.application.domain.model.entity.Topic;
import com.kdbf.forum.application.domain.model.entity.objectValue.CourseCode;
import com.kdbf.forum.application.domain.model.entity.objectValue.TopicStatus;

public class TopicMother {
  public static Topic sample() {
    return Topic.reconstitute(
        new Course("Http fundamentals", new CourseCode("HTP-0987")),
        UUID.randomUUID(),
        "Post requests",
        "¿Whats the difference between post and get?",
        new Author("Web dev",
            "confused_dev"),
        LocalDateTime.now(),
        TopicStatus.DRAFT);
  }

}

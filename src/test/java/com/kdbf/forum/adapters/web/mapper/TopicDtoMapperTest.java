package com.kdbf.forum.adapters.web.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.kdbf.forum.adapters.in.web.dto.AuthorDto;
import com.kdbf.forum.adapters.in.web.dto.CourseDto;
import com.kdbf.forum.adapters.in.web.dto.CreateTopicDto;
import com.kdbf.forum.adapters.in.web.dto.ResponseTopicDto;
import com.kdbf.forum.adapters.in.web.mapper.AuthorDtoMapper;
import com.kdbf.forum.adapters.in.web.mapper.AuthorDtoMapperImpl;
import com.kdbf.forum.adapters.in.web.mapper.CourseDtoMapper;
import com.kdbf.forum.adapters.in.web.mapper.CourseDtoMapperImpl;
import com.kdbf.forum.adapters.in.web.mapper.TopicDtoMapper;
import com.kdbf.forum.adapters.in.web.mapper.TopicDtoMapperImpl;
import com.kdbf.forum.application.domain.model.entity.Author;
import com.kdbf.forum.application.domain.model.entity.Course;
import com.kdbf.forum.application.domain.model.entity.Topic;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    TopicDtoMapperImpl.class,
    AuthorDtoMapperImpl.class,
    CourseDtoMapperImpl.class
})
@ActiveProfiles("test")
public class TopicDtoMapperTest {

  @Autowired
  TopicDtoMapper topicMapper;

  @Autowired
  AuthorDtoMapper authorMapper;

  @Autowired
  CourseDtoMapper courseMapper;

  @Test
  public void shouldConvertToDomainCreate() {
    AuthorDto authorDto = new AuthorDto("reluctant_mapper");
    CourseDto courseDto = new CourseDto("Mapping for dummies");
    CreateTopicDto topicDto = new CreateTopicDto(
        "Do we really need to write so many mappers?",
        "Cant i just let the user talk to the db directly? duh",
        courseDto,
        authorDto);

    Topic topic = topicMapper.toDomain(topicDto);

    assertNotNull(topic);
    assertNotNull(topic.getPublicId());
    assertEquals(topicDto.title(), topic.getTitle());
    assertEquals(topicDto.body(), topic.getBody());
    assertEquals(topicDto.author().username(), topic.getAuthor().getUsername());
    assertEquals(topicDto.course().courseName(), topic.getCourse().getCourseName());
  }

  @Test
  public void shouldConvertToDtoResponse() {
    Author author = new Author("reluctant_mapper");
    Course course = new Course("Mapping for dummies");
    Topic topic = Topic.newInstance(
        course,
        "Do we really need to write so many mappers?",
        "Cant i just let the user talk to the db directly? duh",
        author);

    ResponseTopicDto topicDto = topicMapper.toDto(topic);

    assertNotNull(topicDto);
    assertEquals(topic.getPublicId(), topicDto.publicId());
    assertEquals(topic.getTitle(), topicDto.title());
    assertEquals(topic.getBody(), topicDto.body());
    assertEquals(topic.getCourse().getCourseName(), topicDto.course().courseName());
    assertEquals(topic.getAuthor().getUsername(), topicDto.author().username());
  }

}

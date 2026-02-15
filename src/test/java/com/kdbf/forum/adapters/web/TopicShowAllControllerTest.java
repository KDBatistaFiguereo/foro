package com.kdbf.forum.adapters.web;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.kdbf.forum.adapters.in.web.TopicController;
import com.kdbf.forum.adapters.in.web.dto.AuthorDto;
import com.kdbf.forum.adapters.in.web.dto.CourseDto;
import com.kdbf.forum.adapters.in.web.dto.ResponseTopicDto;
import com.kdbf.forum.adapters.in.web.mapper.TopicDtoMapper;
import com.kdbf.forum.application.domain.model.entity.Author;
import com.kdbf.forum.application.domain.model.entity.Course;
import com.kdbf.forum.application.domain.model.entity.Topic;
import com.kdbf.forum.application.domain.model.entity.objectValue.TopicStatus;
import com.kdbf.forum.application.domain.service.FindTopicsService;
import com.kdbf.forum.application.domain.service.RegisterTopicService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.mockito.Mockito.when;

@WebMvcTest(TopicController.class)
@ActiveProfiles("test")
@WithMockUser
public class TopicShowAllControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockitoBean
  private RegisterTopicService registerTopic;

  @MockitoBean
  private FindTopicsService findTopics;

  @MockitoBean
  private TopicDtoMapper topicMapper;

  @Test
  @DisplayName("Should return 200 and list of topics")
  public void shouldReturnList() throws Exception {
    UUID publicId = UUID.randomUUID();
    String title = "Post requests";
    String body = "¿Whats the difference between post and get?";
    String username = "confused_web_dev";
    String courseName = "Http fundamentals";
    LocalDateTime creationDate = LocalDateTime.now();
    TopicStatus topicStatus = TopicStatus.DRAFT;

    Author author = new Author(username);
    Course course = new Course(courseName);
    Topic topic = Topic.reconstitute(course, publicId, title, body, author, creationDate, topicStatus);
    AuthorDto authorDto = new AuthorDto(username);
    CourseDto courseDto = new CourseDto(courseName);
    ResponseTopicDto responseDto = new ResponseTopicDto(
        publicId,
        title,
        body,
        authorDto,
        courseDto,
        LocalDateTime.now(),
        TopicStatus.DRAFT);

    when(findTopics.findAllTopics()).thenReturn(List.of(topic));
    when(topicMapper.toDto(topic)).thenReturn(responseDto);

    mockMvc.perform(get("/topicos"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].publicId").value(publicId.toString()))
        .andExpect(jsonPath("$[0].title").value(title))
        .andExpect(jsonPath("$[0].body").value(body))
        .andDo(print());

  }

}

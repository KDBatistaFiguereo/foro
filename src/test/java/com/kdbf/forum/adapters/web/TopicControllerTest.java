package com.kdbf.forum.adapters.web;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kdbf.forum.adapters.in.web.TopicController;
import com.kdbf.forum.adapters.in.web.dto.AuthorDto;
import com.kdbf.forum.adapters.in.web.dto.CourseDto;
import com.kdbf.forum.adapters.in.web.dto.ResponseTopicDto;
import com.kdbf.forum.adapters.in.web.mapper.TopicDtoMapper;
import com.kdbf.forum.application.domain.model.entity.Author;
import com.kdbf.forum.application.domain.model.entity.Course;
import com.kdbf.forum.application.domain.model.entity.Topic;
import com.kdbf.forum.application.domain.service.RegisterTopicService;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(TopicController.class)
@WithMockUser
public class TopicControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockitoBean
  private RegisterTopicService topicService;

  @MockitoBean
  private TopicDtoMapper topicMapper;

  @Test
  @DisplayName("Should return 201 if succesful")
  public void registerSuccess() throws Exception {
    UUID publicId = UUID.randomUUID();
    String title = "Post requests";
    String body = "¿Whats the difference between post and get?";
    String username = "confused_web_dev";
    String courseName = "Http fundamentals";
    Author author = new Author(username);
    Course course = new Course(courseName);
    Topic savedTopic = Topic.reconstitute(course, publicId, title, body, author);
    AuthorDto authorDto = new AuthorDto(username);
    CourseDto courseDto = new CourseDto(courseName);
    ResponseTopicDto responseDto = new ResponseTopicDto(publicId, title, body, authorDto, courseDto);

    when(topicService.registerTopic(any()))
        .thenReturn(savedTopic);
    when(topicMapper.toDto(savedTopic))
        .thenReturn(responseDto);

    String json = """
        {
          "title": "%s",
          "body": "%s",
          "author": { "username": "%s" },
          "course": { "courseName": "%s" }
        }
        """.formatted(title, body, username, courseName);

    mockMvc.perform(post("/topicos")
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.publicId").value(publicId.toString()))
        .andExpect(jsonPath("$.title").value(title))
        .andExpect(jsonPath("$.body").value(body))
        .andExpect(jsonPath("$.author.username").value(username))
        .andExpect(jsonPath("$.course.courseName").value(courseName))
        .andDo(print());
  }

}

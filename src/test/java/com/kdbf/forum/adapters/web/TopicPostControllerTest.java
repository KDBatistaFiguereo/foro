package com.kdbf.forum.adapters.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.kdbf.forum.adapters.in.web.TopicController;
import com.kdbf.forum.adapters.in.web.dto.ResponseTopicDto;
import com.kdbf.forum.adapters.in.web.mapper.TopicDtoMapper;
import com.kdbf.forum.adapters.web.mother.TopicDtoMother;
import com.kdbf.forum.adapters.web.mother.TopicMother;
import com.kdbf.forum.application.domain.model.entity.Topic;
import com.kdbf.forum.application.domain.service.DeleteTopicService;
import com.kdbf.forum.application.domain.service.FindTopicsService;
import com.kdbf.forum.application.domain.service.RegisterTopicService;
import com.kdbf.forum.application.domain.service.UpdateTopicService;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(TopicController.class)
@WithMockUser
@ActiveProfiles("test")
public class TopicPostControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockitoBean
  private RegisterTopicService registerService;

  @MockitoBean
  private FindTopicsService findService;

  @MockitoBean
  private UpdateTopicService updateTopic;

  @MockitoBean
  private TopicDtoMapper topicMapper;

  @MockitoBean
  private DeleteTopicService deleteTopic;

  @Test
  @DisplayName("Should return 201 if succesful")
  public void registerSuccess() throws Exception {
    Topic savedTopic = TopicMother.sample();
    ResponseTopicDto responseDto = TopicDtoMother.sample(savedTopic);

    when(registerService.registerTopic(any()))
        .thenReturn(savedTopic);
    when(topicMapper.toDto(savedTopic))
        .thenReturn(responseDto);

    // TODO: Update json to reflect newer fields
    String json = """
        {
          "title": "%s",
          "body": "%s",
          "author": { "username": "%s" },
          "course": { "courseName": "%s" }
        }
        """.formatted(savedTopic.getTitle(),
        savedTopic.getBody(),
        savedTopic.getAuthor().getUsername(),
        savedTopic.getCourse().getCourseName());

    mockMvc.perform(post("/topicos")
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.publicId").value(responseDto.publicId().toString()))
        .andExpect(jsonPath("$.title").value(responseDto.title()))
        .andExpect(jsonPath("$.body").value(responseDto.body()))
        .andExpect(jsonPath("$.author.username").value(responseDto.author().username()))
        .andExpect(jsonPath("$.course.courseName").value(responseDto.course().courseName()))
        .andDo(print());
  }

}

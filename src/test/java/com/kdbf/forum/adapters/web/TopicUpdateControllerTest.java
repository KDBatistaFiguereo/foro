package com.kdbf.forum.adapters.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.kdbf.forum.adapters.out.persistence.repository.AuthorRepository;
import com.kdbf.forum.adapters.web.mother.TopicDtoMother;
import com.kdbf.forum.adapters.web.mother.TopicMother;
import com.kdbf.forum.application.domain.model.entity.Topic;
import com.kdbf.forum.application.domain.service.DeleteTopicService;
import com.kdbf.forum.application.domain.service.FindTopicsService;
import com.kdbf.forum.application.domain.service.RegisterTopicService;
import com.kdbf.forum.application.domain.service.UpdateTopicService;
import com.kdbf.forum.infraestructure.adapter.web.auth.service.TokenService;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(TopicController.class)
@ActiveProfiles("test")
@WithMockUser
public class TopicUpdateControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockitoBean
  private RegisterTopicService registerTopic;

  @MockitoBean
  private FindTopicsService findTopics;

  @MockitoBean
  private UpdateTopicService updateTopic;

  @MockitoBean
  private TopicDtoMapper topicMapper;

  @MockitoBean
  private DeleteTopicService deleteTopic;

  @MockitoBean
  private TokenService tokenService;

  @MockitoBean
  private AuthorRepository authorRepository;

  @Test
  public void shouldUpdateTopic() throws Exception {
    Topic topic = TopicMother.sample();
    ResponseTopicDto response = TopicDtoMother.sample(topic);

    when(updateTopic.updateTopic(any()))
        .thenReturn(topic);
    when(topicMapper.toDto(topic)).thenReturn(response);

    String json = """
        {
          "publicId": "%s",
          "title": "%s",
          "body": "%s"
        }
        """.formatted(topic.getPublicId(),
        topic.getTitle(),
        topic.getBody());

    mockMvc.perform(put("/topicos/" + topic.getPublicId().toString())
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.publicId").value(response.publicId().toString()))
        .andExpect(jsonPath("$.title").value(response.title()))
        .andExpect(jsonPath("$.body").value(response.body()))
        .andDo(print());

  }

}

package com.kdbf.forum.adapters.web;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
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
  @DisplayName("Should return 200 and list of topics")
  public void shouldReturnList() throws Exception {

    Topic topic = TopicMother.sample();
    ResponseTopicDto responseDto = TopicDtoMother.sample(topic);

    when(findTopics.findAllTopics()).thenReturn(List.of(topic));
    when(topicMapper.toDto(topic)).thenReturn(responseDto);

    mockMvc.perform(get("/topicos"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].publicId").value(responseDto.publicId().toString()))
        .andExpect(jsonPath("$[0].title").value(responseDto.title()))
        .andExpect(jsonPath("$[0].body").value(responseDto.body()))
        .andDo(print());

  }

}

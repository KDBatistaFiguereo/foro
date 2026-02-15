package com.kdbf.forum.adapters.web;

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
import com.kdbf.forum.adapters.web.mother.TopicDtoMother;
import com.kdbf.forum.adapters.web.mother.TopicMother;
import com.kdbf.forum.application.domain.model.entity.Topic;
import com.kdbf.forum.application.domain.service.FindTopicsService;
import com.kdbf.forum.application.domain.service.RegisterTopicService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TopicController.class)
@ActiveProfiles("test")
@WithMockUser
public class TopicShowControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockitoBean
  private RegisterTopicService registerTopic;

  @MockitoBean
  private FindTopicsService findTopics;

  @MockitoBean
  private TopicDtoMapper topicMapper;

  @Test
  @DisplayName("Should return 200 and the topic")
  public void shouldReturnTopic() throws Exception {
    Topic topic = TopicMother.sample();
    ResponseTopicDto response = TopicDtoMother.sample(topic);

    when(findTopics.findTopicById(any())).thenReturn(topic);
    when(topicMapper.toDto(topic)).thenReturn(response);

    mockMvc.perform(get("/topicos/" + topic.getPublicId().toString()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.publicId").value(response.publicId().toString()))
        .andExpect(jsonPath("$.title").value(response.title()))
        .andExpect(jsonPath("$.body").value(response.body()))
        .andDo(print());

  }

}

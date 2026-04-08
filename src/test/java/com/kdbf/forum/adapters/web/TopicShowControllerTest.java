package com.kdbf.forum.adapters.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.kdbf.forum.adapters.in.security.JwtSecurityFilter;
import com.kdbf.forum.adapters.in.web.dto.ResponseTopicDto;
import com.kdbf.forum.adapters.in.web.mapper.TopicDtoMapper;
import com.kdbf.forum.adapters.in.web.topic.ShowTopicsController;
import com.kdbf.forum.adapters.web.mother.TopicDtoMother;
import com.kdbf.forum.adapters.web.mother.TopicMother;
import com.kdbf.forum.application.domain.model.entity.Topic;
import com.kdbf.forum.application.domain.service.FindTopicsService;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

@Tag("controller")
@WebMvcTest(value = ShowTopicsController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtSecurityFilter.class))

@ActiveProfiles("test")
@WithMockUser
public class TopicShowControllerTest {

  @Autowired
  MockMvc mockMvc;

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

    mockMvc.perform(get("/topics/" + topic.getPublicId().toString()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.publicId").value(response.publicId().toString()))
        .andExpect(jsonPath("$.title").value(response.title()))
        .andExpect(jsonPath("$.body").value(response.body()))
        .andDo(print());

  }

  @Test
  @DisplayName("Should return 200 and list of topics")
  public void shouldReturnList() throws Exception {

    Topic topic = TopicMother.sample();
    ResponseTopicDto responseDto = TopicDtoMother.sample(topic);

    when(findTopics.findAllTopics()).thenReturn(List.of(topic));
    when(topicMapper.toDto(topic)).thenReturn(responseDto);

    mockMvc.perform(get("/topics"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].publicId").value(responseDto.publicId().toString()))
        .andExpect(jsonPath("$[0].title").value(responseDto.title()))
        .andExpect(jsonPath("$[0].body").value(responseDto.body()))
        .andDo(print());

  }

}

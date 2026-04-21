package com.kdbf.forum.adapters.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.kdbf.forum.adapters.in.security.filter.JwtSecurityFilter;
import com.kdbf.forum.adapters.in.web.topic.UpdateTopicController;
import com.kdbf.forum.adapters.in.web.topic.dto.ResponseTopicDto;
import com.kdbf.forum.adapters.in.web.topic.mapper.TopicDtoMapper;
import com.kdbf.forum.adapters.web.mother.TopicDtoMother;
import com.kdbf.forum.adapters.web.mother.TopicMother;
import com.kdbf.forum.application.domain.model.entity.Topic;
import com.kdbf.forum.application.domain.service.topic.UpdateTopicService;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@Tag("controller")
@WebMvcTest(value = UpdateTopicController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtSecurityFilter.class))

@ActiveProfiles("test")
@WithMockUser
public class TopicUpdateControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockitoBean
  private UpdateTopicService updateTopic;

  @MockitoBean
  private TopicDtoMapper topicMapper;

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

    mockMvc.perform(put("/topics/" + topic.getPublicId().toString())
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

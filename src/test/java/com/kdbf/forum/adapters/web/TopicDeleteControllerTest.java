package com.kdbf.forum.adapters.web;

import java.util.UUID;

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

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import com.kdbf.forum.adapters.in.security.filter.JwtSecurityFilter;
import com.kdbf.forum.adapters.in.web.topic.DeleteTopicController;
import com.kdbf.forum.adapters.in.web.topic.mapper.TopicDtoMapper;
import com.kdbf.forum.application.domain.service.topic.DeleteTopicService;
import com.kdbf.forum.application.port.in.topic.command.DeleteTopicCommand;

@Tag("controller")
@ActiveProfiles("test")
@WithMockUser
@WebMvcTest(value = DeleteTopicController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtSecurityFilter.class))
class TopicControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private DeleteTopicService deleteTopic;

  @MockitoBean
  private TopicDtoMapper topicMapper;

  @Test
  void shouldDeleteTopicAndReturn204() throws Exception {
    UUID publicId = UUID.randomUUID();

    mockMvc.perform(delete("/topics/{publicId}", publicId)
        .with(csrf()))
        .andExpect(status().isNoContent());

    verify(deleteTopic).deleteTopic(new DeleteTopicCommand(publicId));
  }
}

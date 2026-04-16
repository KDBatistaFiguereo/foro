package com.kdbf.forum.adapters.web;

import org.junit.jupiter.api.DisplayName;
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

import com.kdbf.forum.adapters.in.security.JwtSecurityFilter;
import com.kdbf.forum.adapters.in.web.dto.ResponseTopicDto;
import com.kdbf.forum.adapters.in.web.mapper.TopicDtoMapper;
import com.kdbf.forum.adapters.in.web.topic.CreateTopicController;
import com.kdbf.forum.adapters.web.mother.TopicDtoMother;
import com.kdbf.forum.adapters.web.mother.TopicMother;
import com.kdbf.forum.application.domain.model.entity.Topic;
import com.kdbf.forum.application.domain.service.RegisterTopicService;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(value = CreateTopicController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtSecurityFilter.class))
@WithMockUser
@ActiveProfiles("test")
@Tag("controller")
public class CreateTopicControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockitoBean
  private RegisterTopicService registerService;

  @MockitoBean
  private TopicDtoMapper topicMapper;

  @Test
  @DisplayName("Should return 201 if succesful")
  public void registerSuccess() throws Exception {
    Topic savedTopic = TopicMother.sample();
    ResponseTopicDto responseDto = TopicDtoMother.sample(savedTopic);

    when(registerService.registerTopic(any()))
        .thenReturn(savedTopic);
    when(topicMapper.toDto(savedTopic))
        .thenReturn(responseDto);

    String json = """
        {
          "title": "%s",
          "body": "%s",
          "author": { "displayName": "%s",
                      "handle": "%s"
          },
          "course": { "courseName": "%s",
                      "courseCode": "%s"
          }
        }
        """.formatted(savedTopic.getTitle(),
        savedTopic.getBody(),
        savedTopic.getAuthor().getDisplayName(),
        savedTopic.getAuthor().getHandle(),
        savedTopic.getCourse().getCourseName(),
        savedTopic.getCourse().getCourseCode().code());

    mockMvc.perform(post("/topics")
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.publicId").value(responseDto.publicId().toString()))
        .andExpect(jsonPath("$.title").value(responseDto.title()))
        .andExpect(jsonPath("$.body").value(responseDto.body()))
        .andExpect(jsonPath("$.author.displayName").value(responseDto.author().displayName()))
        .andExpect(jsonPath("$.author.handle").value(responseDto.author().handle()))
        .andExpect(jsonPath("$.course.courseName").value(responseDto.course().courseName()))
        .andExpect(jsonPath("$.course.courseCode").value(responseDto.course().courseCode()))

        .andDo(print());
  }

}

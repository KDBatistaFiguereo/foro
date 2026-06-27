package com.kdbf.forum.adapters.in.web.course;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdbf.forum.adapters.in.security.filter.JwtSecurityFilter;
import com.kdbf.forum.adapters.in.web.course.dto.CourseDto;
import com.kdbf.forum.adapters.in.web.course.mapper.CourseDtoMapper;
import com.kdbf.forum.adapters.in.web.course.mapper.CourseDtoMapperImpl;
import com.kdbf.forum.application.domain.model.entity.Course;
import com.kdbf.forum.application.domain.service.course.CreateCourseService;
import com.kdbf.forum.mother.CourseMother;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(value = CreateCourseController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtSecurityFilter.class))
@ActiveProfiles("test")
@Tag("controller")
@WithMockUser(roles = "INSTRUCTOR")
@Import({ CourseDtoMapperImpl.class, ObjectMapper.class })
public class CreateCourseControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockitoBean
  private CreateCourseService createCourse;

  @Autowired
  private CourseDtoMapper courseMapper;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void shouldCreateCourse() throws Exception {
    Course createdCourse = CourseMother.sample();
    when(createCourse.createCourse(any()))
        .thenReturn(createdCourse);
    CourseDto expected = courseMapper.toDto(createdCourse);
    String json = objectMapper.writeValueAsString(expected);

    mockMvc.perform(post("/courses")
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.courseName").value(expected.courseName()))
        .andExpect(jsonPath("$.courseCode").value(expected.courseCode()))
        .andDo(print());
  }

  

  // TODO: Update test
}

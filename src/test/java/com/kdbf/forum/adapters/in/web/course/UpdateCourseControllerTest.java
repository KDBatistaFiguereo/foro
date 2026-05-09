package com.kdbf.forum.adapters.in.web.course;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdbf.forum.adapters.in.security.filter.JwtSecurityFilter;
import com.kdbf.forum.adapters.in.web.course.dto.CourseDto;
import com.kdbf.forum.adapters.in.web.course.dto.UpdateCourseDto;
import com.kdbf.forum.adapters.in.web.course.mapper.CourseDtoMapper;
import com.kdbf.forum.adapters.in.web.course.mapper.CourseDtoMapperImpl;
import com.kdbf.forum.application.domain.model.entity.Course;
import com.kdbf.forum.application.domain.service.course.UpdateCourseService;
import com.kdbf.forum.mother.CourseMother;

@WebMvcTest(value = UpdateCourseController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtSecurityFilter.class))
@Tag("controller")
@WithMockUser
@ActiveProfiles("test")
@Import({
    CourseDtoMapperImpl.class,
    ObjectMapper.class
})
public class UpdateCourseControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockitoBean
  private UpdateCourseService updateCourse;

  @Autowired
  private CourseDtoMapper courseMapper;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void shouldUpdateCourseName() throws Exception {

    Course course = CourseMother.sample();
    Course updatedCourse = CourseMother.customSample(
        "New name",
        course.getCourseCode().code());
    UpdateCourseDto updateDto = courseMapper.toUpdateDto(updatedCourse);

    when(updateCourse.updateCourseName(any()))
        .thenReturn(updatedCourse);

    String json = objectMapper.writeValueAsString(updateDto);
    CourseDto expected = courseMapper.toDto(updatedCourse);

    mockMvc.perform(put("/courses/" + course.getCourseCode().code())
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(json)).andExpect(status().isOk())
        .andExpect(jsonPath("$.courseName").value(expected.courseName()))
        .andDo(print());
  }

}

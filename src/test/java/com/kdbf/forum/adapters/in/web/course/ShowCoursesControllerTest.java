package com.kdbf.forum.adapters.in.web.course;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
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
import com.kdbf.forum.application.domain.service.course.FindCoursesService;
import com.kdbf.forum.mother.CourseMother;

@Tag("controller")
@ActiveProfiles("test")
@WebMvcTest(value = ShowCoursesController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtSecurityFilter.class))
@WithMockUser
@Import({ CourseDtoMapperImpl.class, ObjectMapper.class })
public class ShowCoursesControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockitoBean
  private FindCoursesService findCourses;

  @Autowired
  private CourseDtoMapper courseMapper;

  @Test
  void shouldReturnCourses() throws Exception {

    Course course = CourseMother.sample();
    CourseDto courseDto = courseMapper.toDto(course);

    when(findCourses.findAll())
        .thenReturn(List.of(course));

    mockMvc.perform(get("/courses"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].courseCode").value(courseDto.courseCode()))
        .andExpect(jsonPath("$[0].courseName").value(courseDto.courseName()))
        .andDo(print());

  }

}

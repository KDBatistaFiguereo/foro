package com.kdbf.forum.adapters.in.web.author;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
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
import com.kdbf.forum.adapters.in.web.author.mapper.AuthorDtoMapper;
import com.kdbf.forum.adapters.in.web.author.mapper.AuthorDtoMapperImpl;
import com.kdbf.forum.application.commons.Result;
import com.kdbf.forum.application.domain.model.entity.Author;
import com.kdbf.forum.application.port.in.author.PromoteToInstructorUseCase;
import com.kdbf.forum.mother.AuthorMother;

//TODO: make it so the mock user has to be an admin
//TODO only an admin could do such promotion
@ActiveProfiles("test")
@WithMockUser(roles = "ADMIN")
@Tag("controller")
@WebMvcTest(value = PromoteAuthorController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtSecurityFilter.class))
public class PromoteAuthorToInstructorTest {

  @Autowired
  MockMvc mockMvc;

  @MockitoBean
  PromoteToInstructorUseCase promoteService;

  @MockitoBean
  AuthorDtoMapper authorMapper;

  @Test
  void shouldChangeRoleToInstructor() throws Exception {
    Author author = AuthorMother.sample();
    Author promoted = AuthorMother.sample();

    promoted.promoteToInstructor();

    when(promoteService.promoteToInstructor(any()))
        .thenReturn(new Result<Author>(true, promoted, "johndoe promoted succesfully."));

    mockMvc.perform(post("/authors/{handle}/promote",
        author.getHandle())
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.message").value("johndoe promoted succesfully."));

  }

}

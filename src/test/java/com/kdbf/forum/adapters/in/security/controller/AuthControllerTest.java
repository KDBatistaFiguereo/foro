package com.kdbf.forum.adapters.in.security.controller;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;
import org.springframework.boot.security.autoconfigure.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.kdbf.forum.adapters.in.security.dto.RegistrationDto;
import com.kdbf.forum.adapters.in.security.service.AuthorSignUpService;
import com.kdbf.forum.adapters.in.security.service.TokenService;
import com.kdbf.forum.adapters.out.persistence.entity.AuthorJpa;
import com.kdbf.forum.adapters.out.persistence.repository.AuthorRepository;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class, excludeAutoConfiguration = {
    SecurityAutoConfiguration.class,
    UserDetailsServiceAutoConfiguration.class
})
@WithMockUser
@ActiveProfiles("test")
@Tag("temp")
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockitoBean
  AuthenticationManager manager;

  @MockitoBean
  TokenService tokenService;

  @MockitoBean
  AuthorSignUpService userService;

  @MockitoBean
  AuthorRepository authorRepository;

  @Test
  void signInShouldReturnToken() throws Exception {
    AuthorJpa principal = mock(AuthorJpa.class);
    Authentication auth = mock(Authentication.class);

    when(auth.getPrincipal()).thenReturn(principal);

    when(manager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
    when(tokenService.generateToken(principal)).thenReturn("mocked-jwt-token");

    String json = """
        {
          "username": "testuser",
          "password": "testpass"
        }
        """;

    mockMvc.perform(post("/login")
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").value("mocked-jwt-token"));
  }

  @Test
  void signUpShouldReturnSuccess() throws Exception {
    String json = """
        {
          "displayName": "testuser",
          "username": "test@email.com",
          "password": "testpass"
        }
        """;

    mockMvc.perform(post("/sign-up")
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isOk())
        .andExpect(content().string("User registered succesfully"));

    verify(userService).signUpUser(any(RegistrationDto.class));
  }
}

package com.kdbf.forum.adapters.in.security.filter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;

import com.kdbf.forum.adapters.in.security.service.TokenService;
import com.kdbf.forum.adapters.out.persistence.author.AuthorJpa;
import com.kdbf.forum.adapters.out.persistence.author.AuthorRepository;
import com.kdbf.forum.application.domain.model.entity.UserRoles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Optional;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
public class JwtSecurityFilterTest {

  @InjectMocks
  private JwtSecurityFilter securityFilter;
  @Mock
  private HttpServletRequest servletRequest;
  @Mock
  private HttpServletResponse servletResponse;
  @Mock
  private TokenService tokenService;
  @Mock
  AuthorRepository authorRepository;
  @Mock
  private FilterChain filterChain;

  @Test
  void shouldAuthenticateIfBearerIsValid() throws ServletException, IOException {
    AuthorJpa author = new AuthorJpa("John Doe",
        "johndoe",
        "johndoea@gmail.com",
        "1231435343",
        UserRoles.ROLE_MEMBER);
    when(servletRequest.getHeader("Authorization")).thenReturn("Bearer asadasd93r3");
    when(tokenService.getSubject("asadasd93r3")).thenReturn("johndoea@gmail.com");
    when(authorRepository.findByUsername("johndoea@gmail.com")).thenReturn(Optional.of(author));

    securityFilter.doFilterInternal(servletRequest, servletResponse, filterChain);

    var authentication = SecurityContextHolder.getContext().getAuthentication();

    assertNotNull(authentication);
    assertEquals(author, authentication.getPrincipal());
    assertEquals("ROLE_USER", authentication.getAuthorities().iterator().next().getAuthority());
    verify(filterChain).doFilter(servletRequest, servletResponse);

  }

  @Test
  void shouldNotVerifyIfAuthorizationHeaderIsNull() throws IOException, ServletException {
    when(servletRequest.getHeader("Authorization")).thenReturn(null);

    securityFilter.doFilterInternal(
        servletRequest,
        servletResponse,
        filterChain);
    assertNull(SecurityContextHolder.getContext().getAuthentication());
    verifyNoInteractions(tokenService, authorRepository);
    verify(filterChain).doFilter(servletRequest, servletResponse);

  }

  @Test
  void shouldThrowExceptionSubjectDoesNotExist() throws IOException, ServletException {
    when(servletRequest.getHeader("Authorization")).thenReturn("Bearer ipsjajd8ja08sh8d");
    when(tokenService.getSubject("ipsjajd8ja08sh8d")).thenReturn("johndoea@gmail.com");
    when(authorRepository.findByUsername("johndoea@gmail.com")).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> {
      securityFilter.doFilterInternal(servletRequest, servletResponse, filterChain);
    });

    verify(filterChain, never()).doFilter(servletRequest, servletResponse);
  }

  @Test
  void shouldExtractTokenFromBearer() {
    when(servletRequest.getHeader("Authorization")).thenReturn("Bearer ewjroewjroe");
    String token = securityFilter.returnToken(servletRequest);

    assertEquals("ewjroewjroe", token);
  }

  @ParameterizedTest
  @DisplayName("Should return null if header is null or does not start with bearer")
  @NullSource
  @ValueSource(strings = { "Basic a0siadjsaisssid",
      "",
      "asdadasd",
      "Bearer" })
  void shouldReturnNull(String headerReturn) {
    when(servletRequest.getHeader("Authorization")).thenReturn(null);
    String token = securityFilter.returnToken(servletRequest);
    assertNull(token);
  }

  @Test
  void shouldReturnEmptyString() {
    when(servletRequest.getHeader("Authorization")).thenReturn("Bearer ");
    String token = securityFilter.returnToken(servletRequest);
    assertEquals("", token);
  }
}

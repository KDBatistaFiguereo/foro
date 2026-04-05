package com.kdbf.forum.adapters.in.security.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import com.kdbf.forum.adapters.out.persistence.entity.AuthorJpa;
import com.kdbf.forum.mother.AuthorJpaMother;

@Tag("temp")
@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

  @Mock
  private FindAuthorService findAuthor;

  @InjectMocks
  private AuthenticationService authenticationService;

  @Test
  void shouldReturnUserDetails() {
    String email = "johnDoe@gmail.com";
    AuthorJpa author = AuthorJpaMother.sample();

    when(findAuthor.findByUsername(email)).thenReturn(author);

    UserDetails result = authenticationService.loadUserByUsername(email);

    assertEquals(author, result);
    verify(findAuthor).findByUsername(email);

  }

}

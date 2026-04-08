package com.kdbf.forum.adapters.in.security.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kdbf.forum.adapters.in.security.dto.RegistrationDto;
import com.kdbf.forum.adapters.out.persistence.entity.AuthorJpa;
import com.kdbf.forum.adapters.out.persistence.repository.AuthorRepository;

@ExtendWith(MockitoExtension.class)
public class AuthorSignUpServiceTest {
  @Mock
  private AuthorRepository authorRepository;

  @Mock
  private PasswordEncoder encoder;

  @InjectMocks
  private AuthorSignUpService authorSignUp;

  @Test
  void shouldSaveAuthorWithEncodedPassword() {
    RegistrationDto dto = new RegistrationDto(
        "johndoe",
        "John Doe",
        "johndoe@gmail.com",
        "secret123");
    when(encoder.encode("secret123")).thenReturn("EncodedPassword");

    authorSignUp.signUpUser(dto);

    verify(authorRepository).save(any(AuthorJpa.class));
    verify(encoder).encode("secret123");

  }

}

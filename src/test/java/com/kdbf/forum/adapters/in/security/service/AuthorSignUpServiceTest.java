package com.kdbf.forum.adapters.in.security.service;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kdbf.forum.adapters.in.security.dto.RegistrationDto;
import com.kdbf.forum.adapters.out.persistence.AuthorPersistenceAdapter;
import com.kdbf.forum.application.domain.model.entity.Author;

@Tag("temp")
@ExtendWith(MockitoExtension.class)
public class AuthorSignUpServiceTest {

  @Mock
  private AuthorPersistenceAdapter authorPersistence;
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

    verify(encoder).encode("secret123");
    verify(authorPersistence).registerAuthor(
        eq(new Author("John Doe", "johndoe")),
        eq("johndoe@gmail.com"),
        eq("EncodedPassword"));
  }

}

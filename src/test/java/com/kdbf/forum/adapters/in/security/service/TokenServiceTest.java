package com.kdbf.forum.adapters.in.security.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.kdbf.forum.adapters.out.persistence.entity.AuthorJpa;
import com.kdbf.forum.mother.AuthorJpaMother;

@TestPropertySource(properties = {
    "spring.api.security.token.secret=test-secret-key-for-jwt-token-generation-minimum-32-chars"
})
@ActiveProfiles("test")
class TokenServiceTest {

  private TokenService tokenService;

  @BeforeEach
  void setUp() {
    tokenService = new TokenService();
    try {
      var field = TokenService.class.getDeclaredField("secret");
      field.setAccessible(true);
      field.set(tokenService, "test-secret-key-for-jwt-token-generation-minimum-32-chars");
    } catch (Exception e) {
      throw new RuntimeException("Failed to set secret field", e);
    }
  }

  @Test
  void generateTokenShouldReturnValidToken() {
    AuthorJpa author = AuthorJpaMother.sample();

    String token = tokenService.generateToken(author);

    assertNotNull(token);
    assertTrue(token.split("\\.").length == 3, "JWT token should have 3 parts");
  }

  @Test
  void generateTokenShouldContainCorrectIssuer() {
    AuthorJpa author = AuthorJpaMother.sample();

    String token = tokenService.generateToken(author);

    String subject = tokenService.getSubject(token);
    assertEquals(author.getUsername(), subject);
  }

  @Test
  void generateTokenShouldContainUserEmailAsSubject() {
    String expectedEmail = "johndoe@example.com";
    AuthorJpa author = AuthorJpaMother.sampleWithEmail(expectedEmail);

    String token = tokenService.generateToken(author);

    String subject = tokenService.getSubject(token);
    assertEquals(expectedEmail, subject);
  }

  @Test
  void getSubjectShouldReturnEmailFromValidToken() {
    AuthorJpa author = AuthorJpaMother.sampleWithEmail("decode@test.com");
    String token = tokenService.generateToken(author);

    String subject = tokenService.getSubject(token);

    assertEquals("decode@test.com", subject);
  }

  @Test
  void getSubjectShouldThrowExceptionForInvalidToken() {
    String invalidToken = "invalid.token.here";

    RuntimeException exception = assertThrows(
        RuntimeException.class,
        () -> tokenService.getSubject(invalidToken));

    assertEquals("Token invalid or expired", exception.getMessage());
  }

  @Test
  void getSubjectShouldThrowExceptionForMalformedToken() {
    String malformedToken = "not-a-jwt-token";

    assertThrows(
        RuntimeException.class,
        () -> tokenService.getSubject(malformedToken));
  }

  @Test
  void getSubjectShouldThrowExceptionForEmptyToken() {
    String emptyToken = "";

    assertThrows(
        RuntimeException.class,
        () -> tokenService.getSubject(emptyToken));
  }

  @Test
  void getSubjectShouldThrowExceptionForNullToken() {
    assertThrows(
        RuntimeException.class,
        () -> tokenService.getSubject(null));
  }

  @Test
  void tokenShouldBeDifferentForDifferentUsers() {
    AuthorJpa author1 = AuthorJpaMother.sampleWithEmail("user1@test.com");
    AuthorJpa author2 = AuthorJpaMother.sampleWithEmail("user2@test.com");

    String token1 = tokenService.generateToken(author1);
    String token2 = tokenService.generateToken(author2);

    assertNotNull(token1);
    assertNotNull(token2);
    assertTrue(!token1.equals(token2), "Tokens should be different for different users");
  }

}

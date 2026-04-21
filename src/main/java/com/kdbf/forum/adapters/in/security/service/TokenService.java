package com.kdbf.forum.adapters.in.security.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.kdbf.forum.adapters.out.persistence.author.AuthorJpa;

@Service
public class TokenService {

  @Value("${spring.api.security.token.secret}")
  private String secret;

  public String generateToken(AuthorJpa author) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      return JWT.create()
          .withIssuer("forum")
          .withSubject(author.getUsername())
          .withExpiresAt(expirationDate())
          .sign(algorithm);

    } catch (Exception e) {
      throw new RuntimeException("Error generating token", e);
    }
  }

  public String getSubject(String tokenJwt) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      return JWT.require(algorithm)
          .withIssuer("forum")
          .build()
          .verify(tokenJwt)
          .getSubject();
    } catch (Exception e) {
      throw new RuntimeException("Token invalid or expired", e);
    }
  }

  private Instant expirationDate() {
    return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-04:00"));
  }

}

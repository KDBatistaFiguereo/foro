package com.kdbf.forum.infraestructure.adapter.web.auth;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.kdbf.forum.adapters.out.persistence.entity.AuthorJpa;

@Service
public class TokenService {

  @Value("${spring.api.security.token.secret}")
  private String secret;

  public String generateToken(AuthorJpa author) {
    try {
      var algorithm = Algorithm.HMAC256(secret);
      return JWT.create()
          .withIssuer("forum")
          .withSubject(author.getLogin())
          .withExpiresAt(expirationDate())
          .sign(algorithm);

    } catch (Exception e) {
      throw new RuntimeException("Error generating token", e);
    }
  }

  public String getSubject(String tokenJwt) {
    try {
      var algorithm = Algorithm.HMAC256(secret);
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

package com.kdbf.forum.infraestructure.adapter.web.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kdbf.forum.adapters.out.persistence.entity.AuthorJpa;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/login")
@AllArgsConstructor
public class AuthController {

  AuthenticationManager manager;
  TokenService tokenService;

  @PostMapping
  public ResponseEntity<TokenDto> signIn(@RequestBody @Valid AuthDataDto dto) {

    var token = new UsernamePasswordAuthenticationToken(
        dto.login(),
        dto.password());
    var auth = manager.authenticate(token);

    var jwtToken = tokenService.generateToken((AuthorJpa) auth.getPrincipal());
    return ResponseEntity.ok(new TokenDto(jwtToken));

  }

}

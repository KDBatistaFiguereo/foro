package com.kdbf.forum.adapters.in.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kdbf.forum.adapters.in.security.dto.LoginDto;
import com.kdbf.forum.adapters.in.security.dto.RegistrationDto;
import com.kdbf.forum.adapters.in.security.dto.TokenDto;
import com.kdbf.forum.adapters.in.security.service.TokenService;
import com.kdbf.forum.adapters.out.persistence.author.AuthorJpa;
import com.kdbf.forum.adapters.in.security.service.AuthorSignUpService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class AuthController {

  AuthenticationManager manager;
  TokenService tokenService;
  AuthorSignUpService authorSignUp;

  @PostMapping("/login")
  public ResponseEntity<TokenDto> signIn(@RequestBody @Valid LoginDto dto) {

    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
        dto.username(),
        dto.password());
    Authentication auth = manager.authenticate(token);

    String jwtToken = tokenService.generateToken((AuthorJpa) auth.getPrincipal());
    return ResponseEntity.ok(new TokenDto(jwtToken));

  }

  @PostMapping("/sign-up")
  public ResponseEntity<String> signUp(@RequestBody @Valid RegistrationDto dto) {
    authorSignUp.signUpUser(dto);
    return ResponseEntity.ok("User registered succesfully");
  }

}

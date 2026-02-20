package com.kdbf.forum.infraestructure.adapter.web.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kdbf.forum.adapters.out.persistence.entity.AuthorJpa;
import com.kdbf.forum.infraestructure.adapter.web.auth.dto.AuthDataDto;
import com.kdbf.forum.infraestructure.adapter.web.auth.dto.RegistrationDto;
import com.kdbf.forum.infraestructure.adapter.web.auth.dto.TokenDto;
import com.kdbf.forum.infraestructure.adapter.web.auth.service.TokenService;
import com.kdbf.forum.infraestructure.adapter.web.auth.service.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class AuthController {

  AuthenticationManager manager;
  TokenService tokenService;
  UserService userService;

  @PostMapping("/login")
  public ResponseEntity<TokenDto> signIn(@RequestBody @Valid AuthDataDto dto) {

    var token = new UsernamePasswordAuthenticationToken(
        dto.username(),
        dto.password());
    var auth = manager.authenticate(token);

    var jwtToken = tokenService.generateToken((AuthorJpa) auth.getPrincipal());
    return ResponseEntity.ok(new TokenDto(jwtToken));

  }

  @PostMapping("/sign-up")
  public ResponseEntity<String> signUp(@RequestBody @Valid RegistrationDto dto) {
    userService.signUpUser(dto);
    return ResponseEntity.ok("User registered succesfully");
  }

}

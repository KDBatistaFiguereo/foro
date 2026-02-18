package com.kdbf.forum.infraestructure.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kdbf.forum.adapters.out.persistence.repository.AuthorRepository;
import com.kdbf.forum.infraestructure.adapter.web.auth.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

  TokenService tokenService;
  AuthorRepository authorRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String tokenJwt = returnToken(request);

    if (tokenJwt != null) {
      var subject = tokenService.getSubject(tokenJwt);
      var usuario = authorRepository.findByLogin(subject);
      var auth = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(auth);
    }

    filterChain.doFilter(request, response);

  }

  private String returnToken(HttpServletRequest request) {
    String authorizationHeader = request.getHeader("Authorization");
    if (authorizationHeader != null) {
      return authorizationHeader.replace("Bearer ", "");
    }

    return null;
  }

}

package com.kdbf.forum.adapters.in.security.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kdbf.forum.adapters.in.security.service.TokenService;
import com.kdbf.forum.adapters.out.persistence.author.AuthorRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class JwtSecurityFilter extends OncePerRequestFilter {

  TokenService tokenService;

  AuthorRepository authorRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String tokenJwt = returnToken(request);

    if (tokenJwt != null) {
      var subject = tokenService.getSubject(tokenJwt);
      var author = authorRepository.findByUsername(subject)
          .orElseThrow(() -> new RuntimeException("Author not found"));
      var auth = new UsernamePasswordAuthenticationToken(author, null, author.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(auth);
    }

    filterChain.doFilter(request, response);

  }

  protected String returnToken(HttpServletRequest request) {
    String authorizationHeader = request.getHeader("Authorization");
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      return authorizationHeader.substring(7); // remove first 7 characters
    }

    return null;
  }

}

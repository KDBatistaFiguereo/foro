package com.kdbf.forum.infraestructure.adapter.web.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthenticationService implements UserDetailsService {

  private FindAuthorService findAuthor;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return findAuthor.findByUsername(username);
  }

}

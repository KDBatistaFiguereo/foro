package com.kdbf.forum.adapters.out.persistence.author;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.kdbf.forum.application.domain.model.entity.UserRoles;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "author")
@Setter
@Getter
@NoArgsConstructor
public class AuthorJpa implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "display_name", nullable = false)
  private String displayName;

  @Column(name = "handle", nullable = false)
  private String handle;

  @Column(unique = true, nullable = false, length = 100)
  private String username;

  @Column(name = "password", nullable = false, length = 255)
  private String hashedPassword;

  @Column(name = "role")
  private UserRoles role;

  public AuthorJpa(String displayName,
      String handle,
      String username,
      String hashedPassword,
      UserRoles role) {
    this.displayName = displayName;
    this.handle = handle;
    this.username = username;
    this.hashedPassword = hashedPassword;
    this.role = role;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    AuthorJpa other = (AuthorJpa) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  @Override
  public String getPassword() {
    return this.hashedPassword;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_USER"));
  }

}

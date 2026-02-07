package com.kdbf.forum.adapters.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "AUTHOR")
@Setter
@Getter
@NoArgsConstructor
public class AuthorJpa {

  @Id
  private Long id;

  @Column
  private String username;

  public AuthorJpa(String username) {
    this.username = username;
  }

}

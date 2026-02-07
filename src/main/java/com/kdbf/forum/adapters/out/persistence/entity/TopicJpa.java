package com.kdbf.forum.adapters.out.persistence.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TOPIC")
@NoArgsConstructor
@Setter
@Getter
public class TopicJpa {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "public_id", nullable = false, unique = true, updatable = false)
  private UUID publicId;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String body;

  @ManyToOne
  @JoinColumn(name = "author_id", nullable = false)
  private AuthorJpa author;

  @ManyToOne
  @JoinColumn(name = "course_id", nullable = false)
  private CourseJpa course;

  public TopicJpa(UUID publicId, String title, String body, AuthorJpa author, CourseJpa course) {
    this.publicId = publicId;
    this.title = title;
    this.body = body;
    this.author = author;
    this.course = course;
  }
}

package com.kdbf.forum.adapters.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "COURSE")
@Setter
@Getter
@NoArgsConstructor
public class CourseJpa {

  @Id
  private Long id;

  @Column(name = "course_name")
  private String courseName;

  public CourseJpa(String courseName) {
    this.courseName = courseName;
  }

}

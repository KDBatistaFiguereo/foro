package com.kdbf.forum.adapters.out.persistence.entity;

import com.kdbf.forum.application.domain.model.entity.objectValue.CourseCode;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "course")
@Setter
@Getter
@NoArgsConstructor
public class CourseJpa {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "course_name")
  private String courseName;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "code", column = @Column(name = "course_code"))
  })
  private CourseCode courseCode;

  public CourseJpa(String courseName, CourseCode courseCode) {
    this.courseName = courseName;
    this.courseCode = courseCode;
  }

}

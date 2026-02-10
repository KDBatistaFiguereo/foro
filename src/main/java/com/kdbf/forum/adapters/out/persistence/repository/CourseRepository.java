package com.kdbf.forum.adapters.out.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kdbf.forum.adapters.out.persistence.entity.CourseJpa;

@Repository
public interface CourseRepository extends JpaRepository<CourseJpa, Long> {

  @Query("SELECT c FROM CourseJpa c WHERE c.courseName = :courseName")
  public Optional<CourseJpa> byCourseName(String courseName);

  @Query("""
        SELECT CASE
          WHEN COUNT(c) > 0 THEN true
          ELSE false
        END
        FROM CourseJpa c
        WHERE c.courseName = :courseName
      """)
  public Boolean exists(String courseName);
}

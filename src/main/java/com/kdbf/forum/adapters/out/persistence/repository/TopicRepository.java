package com.kdbf.forum.adapters.out.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kdbf.forum.adapters.out.persistence.entity.TopicJpa;

@Repository
public interface TopicRepository extends JpaRepository<TopicJpa, Long> {

  @Query("""
        SELECT t
        FROM TopicJpa t
        WHERE t.title = :title
        AND t.course.courseName = :courseName
      """)
  public Optional<TopicJpa> byTitleAndCourse(String title, String courseName);

  @Query("""
      SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END
      FROM TopicJpa t
      WHERE t.title = :title
      AND t.course.courseName = :courseName
      """)
  public Boolean exists(String title, String courseName);
}

package com.kdbf.forum.adapters.out.persistence.topic;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kdbf.forum.application.domain.model.entity.objectValue.CourseCode;

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
        SELECT t
        FROM TopicJpa t
        WHERE t.publicId = :publicId
      """)
  public Optional<TopicJpa> byPublicId(UUID publicId);

  @Query("""
      SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END
      FROM TopicJpa t
      WHERE t.title = :title
      AND t.course.courseName = :courseName
      """)
  public boolean exists(String title, String courseName);

  @Query("""
      SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END
      FROM TopicJpa t
      WHERE t.publicId = :publicId
      """)
  public boolean existsByPublicId(UUID publicId);

  public boolean existsByTitleAndCourseCourseCode(String title, CourseCode courseCode);

  public List<TopicJpa> findAllByTitle(String title, Pageable pageable);
}

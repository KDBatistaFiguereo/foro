package com.kdbf.forum.adapters.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.kdbf.forum.adapters.out.persistence.entity.CourseJpa;
import com.kdbf.forum.adapters.out.persistence.mapper.CourseJpaMapper;
import com.kdbf.forum.adapters.out.persistence.repository.CourseRepository;
import com.kdbf.forum.adapters.out.persistence.utility.CycleAvoidingMappingContext;
import com.kdbf.forum.application.domain.model.entity.Course;
import com.kdbf.forum.application.domain.model.entity.objectValue.CourseCode;
import com.kdbf.forum.application.port.out.CourseExistencePort;
import com.kdbf.forum.application.port.out.FindCoursesPort;
import com.kdbf.forum.application.port.out.PersistCoursePort;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Repository
public class CoursePersistenceAdapter implements
    CourseExistencePort, PersistCoursePort, FindCoursesPort {

  private final CourseRepository courseRepository;
  private final CourseJpaMapper courseMapper;
  private final CycleAvoidingMappingContext context = new CycleAvoidingMappingContext();

  @Override
  public Boolean existsByCode(String code) {
    CourseCode courseCode = new CourseCode(code);
    return courseRepository.existsByCourseCode(courseCode);
  }

  @Override
  @Transactional
  public Course persistCourse(Course course) {
    return courseRepository.byCourseCode(course.getCourseCode())
        .map(existingCourse -> {
          courseMapper.updateJpaFromDomain(course, existingCourse, context);
          CourseJpa updated = courseRepository.save(existingCourse);
          return courseMapper.toDomain(updated, context);
        }).orElseGet(() -> {
          CourseJpa newCourse = courseMapper.toJpa(course, context);
          CourseJpa saved = courseRepository.save(newCourse);
          return courseMapper.toDomain(saved, context);
        });
  }

  @Override
  public List<Course> findAll() {
    return courseRepository.findAll().stream()
        .map(x -> courseMapper.toDomain(x, context))
        .toList();
  }

  @Override
  public Optional<Course> findByCode(CourseCode courseCode) {
    return courseRepository.byCourseCode(courseCode)
        .map(x -> courseMapper.toDomain(x, context));
  }

}

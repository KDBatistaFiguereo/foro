package com.kdbf.forum.adapters.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.kdbf.forum.adapters.out.persistence.course.CourseJpa;
import com.kdbf.forum.adapters.out.persistence.course.CoursePersistenceAdapter;
import com.kdbf.forum.adapters.out.persistence.course.CourseRepository;
import com.kdbf.forum.application.domain.model.entity.Course;
import com.kdbf.forum.mother.CourseMother;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@Testcontainers
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@Tag("persistence")
@AutoConfigureMockMvc(addFilters = false)
public class CoursePersistenceAdapterTest {

  @Autowired
  CoursePersistenceAdapter coursePersistence;

  @Autowired
  CourseRepository courseRepository;

  @Test
  void shouldUpdateCourse() {

    Course course = CourseMother.customSample("Programming essentials", "CSA-0015");
    Course updatedCourse = CourseMother.customSample(
        "Programming basics",
        "CSA-0015");

    coursePersistence.persistCourse(course);
    coursePersistence.persistCourse(updatedCourse);

    List<CourseJpa> courses = courseRepository.findAllByCourseCode(course.getCourseCode());

    assertThat(courses).hasSize(1);
    assertEquals(courses.get(0).getCourseName(), updatedCourse.getCourseName());
  }
}

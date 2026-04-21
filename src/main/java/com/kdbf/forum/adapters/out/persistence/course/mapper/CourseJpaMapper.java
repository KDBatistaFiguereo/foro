package com.kdbf.forum.adapters.out.persistence.course.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.kdbf.forum.adapters.out.persistence.course.CourseJpa;
import com.kdbf.forum.adapters.out.persistence.utility.CycleAvoidingMappingContext;
import com.kdbf.forum.application.domain.model.entity.Course;

@Mapper(componentModel = "spring")
public abstract class CourseJpaMapper {

  abstract public Course toDomain(CourseJpa courseJpa, @Context CycleAvoidingMappingContext context);

  abstract public CourseJpa toJpa(Course course, @Context CycleAvoidingMappingContext context);

  @Mapping(target = "courseCode", ignore = true)
  @Mapping(target = "id", ignore = true)
  abstract public void updateJpaFromDomain(Course course,
      @MappingTarget CourseJpa courseJpa,
      @Context CycleAvoidingMappingContext context);

}

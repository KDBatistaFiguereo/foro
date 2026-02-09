package com.kdbf.forum.adapters.out.persistence.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;

import com.kdbf.forum.adapters.out.persistence.entity.CourseJpa;
import com.kdbf.forum.adapters.out.persistence.utility.CycleAvoidingMappingContext;
import com.kdbf.forum.application.domain.model.entity.Course;

@Mapper(componentModel = "spring")
public abstract class CourseJpaMapper {

  abstract Course toDomain(CourseJpa courseJpa, @Context CycleAvoidingMappingContext context);

  abstract CourseJpa toJpa(Course course, @Context CycleAvoidingMappingContext context);

}

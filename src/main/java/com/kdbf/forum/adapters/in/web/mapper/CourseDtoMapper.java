package com.kdbf.forum.adapters.in.web.mapper;

import org.mapstruct.Mapper;

import com.kdbf.forum.adapters.in.web.dto.CourseDto;
import com.kdbf.forum.application.domain.model.entity.Course;

@Mapper(componentModel = "spring")
public abstract class CourseDtoMapper {

  abstract Course toDomain(CourseDto courseDto);

  abstract CourseDto toDto(Course course);

}

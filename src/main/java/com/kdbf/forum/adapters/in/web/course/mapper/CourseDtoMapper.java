package com.kdbf.forum.adapters.in.web.course.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.kdbf.forum.adapters.in.web.course.dto.CourseDto;
import com.kdbf.forum.application.domain.model.entity.Course;
import com.kdbf.forum.application.domain.model.entity.objectValue.CourseCode;

@Mapper(componentModel = "spring")
public abstract class CourseDtoMapper {

  @Mapping(source = "courseCode", target = "courseCode", qualifiedByName = "codeFromString")
  public abstract Course toDomain(CourseDto courseDto);

  @Named("codeFromString")
  protected CourseCode stringToCode(String code) {
    return new CourseCode(code);
  }

  @Mapping(source = "courseCode", target = "courseCode", qualifiedByName = "stringFromCode")
  public abstract CourseDto toDto(Course course);

  @Named("stringFromCode")
  protected String codeToString(CourseCode courseCode) {
    return courseCode.code();
  }

}

package com.kdbf.forum.adapters.in.web.mapper;

import org.mapstruct.Mapper;

import com.kdbf.forum.adapters.in.web.dto.AuthorDto;
import com.kdbf.forum.application.domain.model.entity.Author;

@Mapper(componentModel = "spring")
public abstract class AuthorDtoMapper {

  abstract Author toDomain(AuthorDto authorDto);

  abstract AuthorDto toDto(Author author);

}

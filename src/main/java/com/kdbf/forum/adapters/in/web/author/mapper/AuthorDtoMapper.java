package com.kdbf.forum.adapters.in.web.author.mapper;

import org.mapstruct.Mapper;

import com.kdbf.forum.adapters.in.web.author.dto.AuthorDto;
import com.kdbf.forum.application.domain.model.entity.Author;

@Mapper(componentModel = "spring")
public abstract class AuthorDtoMapper {

  abstract public Author toDomain(AuthorDto authorDto);

  abstract protected AuthorDto toDto(Author author);

}

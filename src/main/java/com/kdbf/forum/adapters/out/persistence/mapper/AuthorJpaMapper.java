package com.kdbf.forum.adapters.out.persistence.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.kdbf.forum.adapters.out.persistence.entity.AuthorJpa;
import com.kdbf.forum.adapters.out.persistence.utility.CycleAvoidingMappingContext;
import com.kdbf.forum.application.domain.model.entity.Author;

@Mapper(componentModel = "spring")
public abstract class AuthorJpaMapper {

  abstract Author toDomain(AuthorJpa authorJpa, @Context CycleAvoidingMappingContext context);

  // avoid the jpa fields from being nulled
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "login", ignore = true)
  @Mapping(target = "hashedPassword", ignore = true)
  abstract AuthorJpa toJpa(Author author, @Context CycleAvoidingMappingContext context);

}

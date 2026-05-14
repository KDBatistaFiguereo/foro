package com.kdbf.forum.adapters.out.persistence.author.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.kdbf.forum.adapters.out.persistence.author.AuthorJpa;
import com.kdbf.forum.adapters.out.persistence.utility.CycleAvoidingMappingContext;
import com.kdbf.forum.application.domain.model.entity.Author;

@Mapper(componentModel = "spring")
public abstract class AuthorJpaMapper {

  abstract public Author toDomain(AuthorJpa authorJpa, @Context CycleAvoidingMappingContext context);

  // avoid the jpa fields from being nulled
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "username", ignore = true)
  @Mapping(target = "hashedPassword", ignore = true)
  @Mapping(target = "authorities", ignore = true)
  abstract public AuthorJpa toJpa(Author author, @Context CycleAvoidingMappingContext context);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "authorities", ignore = true)
  @Mapping(target = "username", source = "email")
  @Mapping(target = "hashedPassword", source = "hashedPassword")
  abstract public AuthorJpa toJpa(Author author,
      @Context CycleAvoidingMappingContext context,
      String email,
      String hashedPassword);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "handle", ignore = true)
  @Mapping(target = "authorities", ignore = true)
  @Mapping(target = "username", ignore = true)
  @Mapping(target = "hashedPassword", ignore = true)
  abstract public void updateJpaFromDomain(
      Author author,
      @MappingTarget AuthorJpa authorJpa,
      @Context CycleAvoidingMappingContext context);
}

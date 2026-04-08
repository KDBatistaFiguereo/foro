// package com.kdbf.forum.application.domain.service;
//
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
//
// import org.junit.jupiter.api.Tag;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
//
// import com.kdbf.forum.application.domain.model.entity.Author;
// import com.kdbf.forum.application.port.in.CreateAuthorIdentityCommand;
// import com.kdbf.forum.application.port.in.CreateAuthorIdentityUseCase;
//
// @Tag("temp")
// public class CreateAuthorIdentityUseCaseTest {
//
// @Autowired
// CreateAuthorIdentityUseCase createIdentity;
//
// @Test
// void shouldReturnAuthorIdentity() {
// var command = new CreateAuthorIdentityCommand(
// "John Doe",
// "johndoe123");
// Author author = createIdentity.createIdentity(command);
//
// assertNotNull(author);
// assertEquals(command.displayName(), author.getDisplayName());
// assertEquals(command.handle(), author.getHandle());
// }
// }

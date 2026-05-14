package com.kdbf.forum.adapters.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.kdbf.forum.adapters.out.persistence.author.AuthorPersistenceAdapter;
import com.kdbf.forum.adapters.out.persistence.author.AuthorRepository;
import com.kdbf.forum.adapters.out.persistence.author.mapper.AuthorJpaMapper;
import com.kdbf.forum.application.domain.model.entity.Author;
import com.kdbf.forum.mother.AuthorMother;

@ActiveProfiles("test")
@Testcontainers
@Tag("persistence")
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc(addFilters = false)
public class AuthorPersistenceAdapterTest {

  @Autowired
  AuthorPersistenceAdapter authorAdapter;

  @Autowired
  AuthorJpaMapper authorMapper;

  @Autowired
  AuthorRepository authorRepository;

  @Test
  void shouldUpdateExistingAuthor() {
    Author author = AuthorMother.customSample("john", "johndoe");
    authorAdapter.registerAuthor(
        author,
        "john@gmail.com",
        "secretpass");

    // Same handle
    Author updatedAuthor = AuthorMother.customSample("Albert", "johndoe");
    updatedAuthor.promoteToInstructor();

    Author result = authorAdapter.updateAuthor(updatedAuthor);
    int amountCheck = authorRepository.countByHandle("johndoe");

    assertEquals(1, amountCheck);
    assertEquals(updatedAuthor.getRole(), result.getRole());
    assertEquals(updatedAuthor.getDisplayName(), result.getDisplayName());

  }
}

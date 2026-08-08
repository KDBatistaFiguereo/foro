package com.kdbf.forum.application.domain.service.topic;

import com.kdbf.forum.application.domain.model.entity.Author;
import com.kdbf.forum.application.port.in.topic.BookmarkTopicUseCase;
import com.kdbf.forum.application.port.in.topic.command.BookmarkTopicCommand;
import com.kdbf.forum.application.port.out.author.AuthorUpdatePort;
import com.kdbf.forum.application.port.out.author.FindAuthorsPort;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BookmarkTopicService implements
    BookmarkTopicUseCase {

  private final FindAuthorsPort findAuthors;

  private final AuthorUpdatePort updateAuthor;

  // TODO: add edge case author not found
  // TODO: exception handling
  // TODO: test
  // TODO: add validation
  @Override
  public void boomarkTopic(BookmarkTopicCommand command) {
    Author author = findAuthors.findByHandle(command.userHandle()).get();

    author.addToBookmarks(command.topicId());

    updateAuthor.updateAuthor(author);
  }

}

package com.kdbf.forum.application.domain.service.topic;

import com.kdbf.forum.application.domain.model.entity.Author;
import com.kdbf.forum.application.port.in.topic.BookmarkTopicUseCase;
import com.kdbf.forum.application.port.in.topic.command.BookmarkTopicCommand;
import com.kdbf.forum.application.port.out.author.AuthorUpdatePort;
import com.kdbf.forum.application.port.out.author.FindAuthorsPort;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BookmarkTopicService implements BookmarkTopicUseCase {

  FindAuthorsPort findAuthor;
  AuthorUpdatePort updateAuthor;

  @Override
  public void bookmarkTopic(BookmarkTopicCommand command) {
    // TODO: add empty handling
    Author author = findAuthor.findByHandle(command.userHandle()).get();
    author.addToBookmarks(command.topicId());
    updateAuthor.updateAuthor(author);

  }

}

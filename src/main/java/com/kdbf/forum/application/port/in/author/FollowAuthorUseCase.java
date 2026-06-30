package com.kdbf.forum.application.port.in.author;

import com.kdbf.forum.application.port.in.author.command.FollowAuthorCommand;

public interface FollowAuthorUseCase {
  void followAuthor(FollowAuthorCommand command);
}

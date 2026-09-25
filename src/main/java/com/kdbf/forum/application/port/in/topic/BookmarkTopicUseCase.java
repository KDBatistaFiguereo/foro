package com.kdbf.forum.application.port.in.topic;

import com.kdbf.forum.application.port.in.topic.command.BookmarkTopicCommand;

public interface BookmarkTopicUseCase {

  void bookmarkTopic(BookmarkTopicCommand command);

}

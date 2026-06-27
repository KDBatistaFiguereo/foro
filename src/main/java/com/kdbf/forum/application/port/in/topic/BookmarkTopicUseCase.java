package com.kdbf.forum.application.port.in.topic;

public interface BookmarkTopicUseCase {
  void bookmarkTopic(BookmarkTopicCommand command);
}

// an author has alist of bookmarked topics, this adds such topic to the
// author's
// query or command

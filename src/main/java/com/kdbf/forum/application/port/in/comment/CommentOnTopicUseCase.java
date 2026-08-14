package com.kdbf.forum.application.port.in.comment;

import com.kdbf.forum.application.port.in.comment.command.CommentOnTopicCommand;

public interface CommentOnTopicUseCase {

  Comment commentOn(CommentOnTopicCommand command);

}

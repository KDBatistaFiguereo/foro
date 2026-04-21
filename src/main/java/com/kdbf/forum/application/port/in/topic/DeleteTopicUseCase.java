package com.kdbf.forum.application.port.in.topic;

import com.kdbf.forum.application.port.in.topic.command.DeleteTopicCommand;

public interface DeleteTopicUseCase {

  void deleteTopic(DeleteTopicCommand command);

}

package com.kdbf.forum.application.port.in.topic;

import com.kdbf.forum.application.domain.model.entity.Topic;
import com.kdbf.forum.application.port.in.topic.command.RegisterTopicCommand;

public interface RegisterTopicUseCase {
  Topic registerTopic(RegisterTopicCommand command);
}

package com.kdbf.forum.application.port.in;

import com.kdbf.forum.application.domain.model.entity.Topic;

public interface RegisterTopicUseCase {
  Topic registerTopic(RegisterTopicCommand command);
}

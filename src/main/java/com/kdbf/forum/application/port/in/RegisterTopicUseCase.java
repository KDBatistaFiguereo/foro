package com.kdbf.forum.application.port.in;

public interface RegisterTopicUseCase {
  Topic registerTopic(RegisterTopicCommand command);
}

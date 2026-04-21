package com.kdbf.forum.application.port.in.topic;

import com.kdbf.forum.application.domain.model.entity.Topic;
import com.kdbf.forum.application.port.in.topic.command.UpdateTopicCommand;

public interface UpdateTopicUseCase {

  public Topic updateTopic(UpdateTopicCommand command);
}

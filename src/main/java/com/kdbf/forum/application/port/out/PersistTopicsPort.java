package com.kdbf.forum.application.port.out;

import com.kdbf.forum.application.domain.model.entity.Topic;

public interface PersistTopicsPort {
  Topic persistTopic(Topic topic);

}

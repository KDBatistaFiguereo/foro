package com.kdbf.forum.application.port.in.topic.command;

import java.util.UUID;

public record BookMarkTopicCommand(
    UUID topicId,
    String userHandle) { // as in, the user who wishes to bookmark this topic
}

package com.kdbf.forum.application.port.in.topic.command;

import java.util.UUID;

public record BookmarkTopicCommand(
    String userHandle,
    UUID topicId) {
}

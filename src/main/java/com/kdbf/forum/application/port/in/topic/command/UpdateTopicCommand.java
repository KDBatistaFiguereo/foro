package com.kdbf.forum.application.port.in.topic.command;

import java.util.UUID;

public record UpdateTopicCommand(
    UUID publicId,
    String title,
    String body) {

}

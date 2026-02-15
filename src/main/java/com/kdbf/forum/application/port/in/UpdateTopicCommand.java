package com.kdbf.forum.application.port.in;

import java.util.UUID;

import com.kdbf.forum.application.domain.model.entity.objectValue.TopicStatus;

public record UpdateTopicCommand(
    UUID publicId,
    String title,
    String body) {

}

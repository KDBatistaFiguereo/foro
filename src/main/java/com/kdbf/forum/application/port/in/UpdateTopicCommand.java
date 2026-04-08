package com.kdbf.forum.application.port.in;

import java.util.UUID;

public record UpdateTopicCommand(
    UUID publicId,
    String title,
    String body) {

}

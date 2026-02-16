package com.kdbf.forum.application.port.in;

import java.util.UUID;

public record DeleteTopicCommand(
    UUID publicId) {

}

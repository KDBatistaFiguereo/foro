package com.kdbf.forum.application.port.in.comment.command;

import java.util.UUID;

/**
 * CommentOnTopicCommand
 */
public record CommentOnTopicCommand(
    String commentBody,
    UUID topicId,
    String authorHandle) {
}

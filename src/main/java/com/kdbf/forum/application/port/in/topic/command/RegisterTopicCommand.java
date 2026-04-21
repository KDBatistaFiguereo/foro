package com.kdbf.forum.application.port.in.topic.command;

public record RegisterTopicCommand(
    String title,
    String body,
    String authorHandle,
    String courseCode) {

}

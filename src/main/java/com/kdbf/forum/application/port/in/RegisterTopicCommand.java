package com.kdbf.forum.application.port.in;

public record RegisterTopicCommand(
    String title,
    String body,
    String author,
    String course) {

}

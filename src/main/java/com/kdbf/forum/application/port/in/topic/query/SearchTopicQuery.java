package com.kdbf.forum.application.port.in.topic.query;

public record SearchTopicQuery(
    String searchTerm,
    int pageNumber,
    int pageSize) {
}

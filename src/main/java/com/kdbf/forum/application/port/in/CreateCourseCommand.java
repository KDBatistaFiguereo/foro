package com.kdbf.forum.application.port.in;

public record CreateCourseCommand(
    String courseCode,
    String courseName) {

}

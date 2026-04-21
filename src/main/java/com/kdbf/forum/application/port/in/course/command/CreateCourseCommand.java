package com.kdbf.forum.application.port.in.course.command;

public record CreateCourseCommand(
    String courseCode,
    String courseName) {

}

package com.kdbf.forum.application.port.in.course.command;

public record UpdateCourseCommand(
    String courseCode,
    String newCourseName) {
}

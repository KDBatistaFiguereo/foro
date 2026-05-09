package com.kdbf.forum.application.commons;

public record Result<T>(
    boolean success,
    T value,
    String message) {
}

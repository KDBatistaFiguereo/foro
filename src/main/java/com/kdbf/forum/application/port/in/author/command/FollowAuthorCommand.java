package com.kdbf.forum.application.port.in.author.command;

public record FollowAuthorCommand(
    String followerHandle,
    String followedHandle) {

}

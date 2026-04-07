package com.kdbf.forum.application.port.in;

public record CreateAuthorIdentityCommand(
    String displayName,
    String handle) {

}

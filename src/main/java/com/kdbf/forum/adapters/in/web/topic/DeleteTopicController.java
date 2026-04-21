package com.kdbf.forum.adapters.in.web.topic;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.kdbf.forum.application.port.in.topic.DeleteTopicUseCase;
import com.kdbf.forum.application.port.in.topic.command.DeleteTopicCommand;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class DeleteTopicController {
  private final DeleteTopicUseCase deleteTopic;

  @DeleteMapping("/topics/{publicId}")
  public ResponseEntity<Void> deleteTopic(@PathVariable("publicId") UUID publicId) {
    DeleteTopicCommand command = new DeleteTopicCommand(publicId);
    deleteTopic.deleteTopic(command);

    return ResponseEntity
        .noContent()
        .build();
  }

}

package com.kdbf.forum.adapters.in.web.topic;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kdbf.forum.adapters.in.web.topic.dto.ResponseTopicDto;
import com.kdbf.forum.adapters.in.web.topic.dto.UpdateTopicDto;
import com.kdbf.forum.adapters.in.web.topic.mapper.TopicDtoMapper;
import com.kdbf.forum.application.domain.model.entity.Topic;
import com.kdbf.forum.application.port.in.topic.UpdateTopicUseCase;
import com.kdbf.forum.application.port.in.topic.command.UpdateTopicCommand;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UpdateTopicController {
  private final TopicDtoMapper topicMapper;
  private final UpdateTopicUseCase updateTopic;

  @PutMapping("/topics/{publicId}")
  public ResponseEntity<ResponseTopicDto> updateTopic(
      @PathVariable("publicId") UUID publicId,
      @RequestBody @Valid UpdateTopicDto topicDto) {

    UpdateTopicCommand command = new UpdateTopicCommand(
        publicId,
        topicDto.title(),
        topicDto.body());
    Topic updatedTopic = updateTopic.updateTopic(command);
    ResponseTopicDto response = topicMapper.toDto(updatedTopic);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(response);
  }
}

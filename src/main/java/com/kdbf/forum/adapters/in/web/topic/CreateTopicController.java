package com.kdbf.forum.adapters.in.web.topic;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kdbf.forum.adapters.in.web.topic.dto.CreateTopicDto;
import com.kdbf.forum.adapters.in.web.topic.dto.ResponseTopicDto;
import com.kdbf.forum.adapters.in.web.topic.mapper.TopicDtoMapper;
import com.kdbf.forum.application.domain.model.entity.Topic;
import com.kdbf.forum.application.port.in.topic.RegisterTopicUseCase;
import com.kdbf.forum.application.port.in.topic.command.RegisterTopicCommand;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class CreateTopicController {
  private final RegisterTopicUseCase registerTopic;
  private final TopicDtoMapper topicMapper;

  @PostMapping("/topics")
  public ResponseEntity<ResponseTopicDto> registerTopic(
      @RequestBody @Valid CreateTopicDto topicDto) {

    ResponseTopicDto responseDto = processAndSaveTopic(topicDto);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(responseDto);
  }

  private ResponseTopicDto processAndSaveTopic(CreateTopicDto topicDto) {
    RegisterTopicCommand command = new RegisterTopicCommand(
        topicDto.title(),
        topicDto.body(),
        topicDto.author().handle(),
        topicDto.course().courseCode());
    Topic savedTopic = registerTopic.registerTopic(command);

    ResponseTopicDto responseDto = topicMapper.toDto(savedTopic);
    return responseDto;
  }
}

package com.kdbf.forum.adapters.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.kdbf.forum.adapters.in.web.dto.CreateTopicDto;
import com.kdbf.forum.adapters.in.web.dto.ResponseTopicDto;
import com.kdbf.forum.adapters.in.web.mapper.TopicDtoMapper;
import com.kdbf.forum.application.domain.model.entity.Topic;
import com.kdbf.forum.application.domain.service.RegisterTopicService;
import com.kdbf.forum.application.port.in.RegisterTopicCommand;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class TopicController {

  private final TopicDtoMapper topicMapper;
  private final RegisterTopicService topicService;

  @PostMapping("/topicos")
  public ResponseEntity<ResponseTopicDto> registerAuthor(
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
        topicDto.author().username(),
        topicDto.course().courseName());
    Topic savedTopic = topicService.registerTopic(command);

    ResponseTopicDto responseDto = topicMapper.toDto(savedTopic);
    return responseDto;
  }
}

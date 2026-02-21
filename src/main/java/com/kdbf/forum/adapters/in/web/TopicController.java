package com.kdbf.forum.adapters.in.web;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kdbf.forum.adapters.in.web.dto.CreateTopicDto;
import com.kdbf.forum.adapters.in.web.dto.ResponseTopicDto;
import com.kdbf.forum.adapters.in.web.dto.UpdateTopicDto;
import com.kdbf.forum.adapters.in.web.mapper.TopicDtoMapper;
import com.kdbf.forum.application.domain.model.entity.Topic;
import com.kdbf.forum.application.domain.service.DeleteTopicService;
import com.kdbf.forum.application.domain.service.FindTopicsService;
import com.kdbf.forum.application.domain.service.RegisterTopicService;
import com.kdbf.forum.application.domain.service.UpdateTopicService;
import com.kdbf.forum.application.port.in.DeleteTopicCommand;
import com.kdbf.forum.application.port.in.FindTopicByIdQuery;
import com.kdbf.forum.application.port.in.RegisterTopicCommand;
import com.kdbf.forum.application.port.in.UpdateTopicCommand;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class TopicController {

  private final TopicDtoMapper topicMapper;
  private final RegisterTopicService registerService;
  private final FindTopicsService findService;
  private final UpdateTopicService updateTopic;
  private final DeleteTopicService deleteTopic;

  @PostMapping("/topicos")
  public ResponseEntity<ResponseTopicDto> registerAuthor(
      @RequestBody @Valid CreateTopicDto topicDto) {

    ResponseTopicDto responseDto = processAndSaveTopic(topicDto);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(responseDto);

  }

  @GetMapping("/topicos")
  public ResponseEntity<List<ResponseTopicDto>> showAllTopics() {

    List<ResponseTopicDto> topics;

    topics = findService.findAllTopics().stream()
        .map(x -> topicMapper.toDto(x))
        .toList();
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(topics);

  }

  @GetMapping("/topicos/{publicId}")
  public ResponseEntity<ResponseTopicDto> showTopic(
      @PathVariable("publicId") UUID publicId) {

    FindTopicByIdQuery query = new FindTopicByIdQuery(publicId);
    ResponseTopicDto response = topicMapper.toDto(findService.findTopicById(query));

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(response);
  }

  @PutMapping("/topicos/{publicId}")
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

  @DeleteMapping("/topicos/{publicId}")
  public ResponseEntity<Void> deleteTopic(@PathVariable("publicId") UUID publicId) {
    DeleteTopicCommand command = new DeleteTopicCommand(publicId);
    deleteTopic.deleteTopic(command);

    return ResponseEntity
        .noContent()
        .build();
  }

  private ResponseTopicDto processAndSaveTopic(CreateTopicDto topicDto) {
    RegisterTopicCommand command = new RegisterTopicCommand(
        topicDto.title(),
        topicDto.body(),
        topicDto.author().displayName(),
        topicDto.course().courseName(),
        topicDto.course().courseCode());
    Topic savedTopic = registerService.registerTopic(command);

    ResponseTopicDto responseDto = topicMapper.toDto(savedTopic);
    return responseDto;
  }
}

package com.kdbf.forum.adapters.in.web.topic;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.kdbf.forum.adapters.in.web.dto.ResponseTopicDto;
import com.kdbf.forum.adapters.in.web.mapper.TopicDtoMapper;
import com.kdbf.forum.application.port.in.FindTopicByIdQuery;
import com.kdbf.forum.application.port.in.FindTopicsUseCase;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ShowTopicsController {
  private final FindTopicsUseCase findTopic;
  private final TopicDtoMapper topicMapper;

  @GetMapping("/topics")
  public ResponseEntity<List<ResponseTopicDto>> showAllTopics() {

    List<ResponseTopicDto> topics;

    topics = findTopic.findAllTopics().stream()
        .map(x -> topicMapper.toDto(x))
        .toList();
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(topics);

  }

  @GetMapping("/topics/{publicId}")
  public ResponseEntity<ResponseTopicDto> showTopic(
      @PathVariable("publicId") UUID publicId) {

    FindTopicByIdQuery query = new FindTopicByIdQuery(publicId);
    ResponseTopicDto response = topicMapper.toDto(findTopic.findTopicById(query));

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(response);
  }
}

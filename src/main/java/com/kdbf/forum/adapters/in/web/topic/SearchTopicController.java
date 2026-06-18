package com.kdbf.forum.adapters.in.web.topic;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kdbf.forum.adapters.in.web.topic.dto.ResponseTopicDto;
import com.kdbf.forum.adapters.in.web.topic.mapper.TopicDtoMapper;
import com.kdbf.forum.application.port.in.topic.SearchTopicUseCase;
import com.kdbf.forum.application.port.in.topic.query.SearchTopicQuery;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class SearchTopicController {

  SearchTopicUseCase searchTopicUseCase;
  TopicDtoMapper topicDtoMapper;

  @GetMapping("topics/search")
  public ResponseEntity<List<ResponseTopicDto>> searchTopicsByTitle(
      @RequestParam String searchTerm,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int size) {

    SearchTopicQuery query = new SearchTopicQuery(
        searchTerm, page, size);

    List<ResponseTopicDto> topics = searchTopicUseCase.searchTopic(query)
        .stream()
        .map(x -> topicDtoMapper.toDto(x))
        .toList();

    return ResponseEntity.ok(topics);
  }
}

package com.kdbf.forum.application.domain.service.topic;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.kdbf.forum.application.domain.model.entity.Topic;
import com.kdbf.forum.application.port.in.topic.SearchTopicUseCase;
import com.kdbf.forum.application.port.in.topic.query.SearchTopicQuery;
import com.kdbf.forum.application.port.out.topic.FindTopicsPort;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SearchTopicService implements SearchTopicUseCase {

  FindTopicsPort findTopicsPort;

  @Override
  public List<Topic> searchTopic(SearchTopicQuery query) {
    List<Topic> topics = findTopicsPort.findAllByTitle(
        query.searchTerm(),
        PageRequest.of(
            query.pageNumber(),
            query.pageSize()));

    return topics;
  }

}

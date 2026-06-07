package com.kdbf.forum.application.domain.service.topic;

import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import com.kdbf.forum.adapters.web.mother.TopicMother;
import com.kdbf.forum.application.domain.model.entity.Topic;
import com.kdbf.forum.application.port.in.topic.query.SearchTopicQuery;
import com.kdbf.forum.application.port.out.topic.FindTopicsPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@Tag("temp")
@ExtendWith(MockitoExtension.class)
public class SearchTopicServiceTest {

  @Mock
  FindTopicsPort findTopics;

  @InjectMocks
  SearchTopicService searchService;

  @Test
  void shouldReturnTopicsWhenSearchTermMatches() {
    SearchTopicQuery query = new SearchTopicQuery(
        "code",
        0,
        5);

    List<Topic> expected = List.of(TopicMother.sample());

    when(findTopics.findAllByTitle(eq("code"), any(PageRequest.class)))
        .thenReturn(expected);

    List<Topic> result = searchService.searchTopic(query);

    assertEquals(1, result.size());
    assertEquals(expected, result);
  }

}

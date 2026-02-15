package com.kdbf.forum.application.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kdbf.forum.application.domain.model.entity.Topic;
import com.kdbf.forum.application.port.in.FindTopicsUseCase;
import com.kdbf.forum.application.port.out.FindTopicsPort;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class FindTopicsService implements FindTopicsUseCase {

  private final FindTopicsPort findTopicsPort;

  @Override
  public List<Topic> findAllTopics() {
    return findTopicsPort.findAll();
  }

}

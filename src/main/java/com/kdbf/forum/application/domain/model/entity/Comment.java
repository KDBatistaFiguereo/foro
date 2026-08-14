package com.kdbf.forum.application.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Comment {

  String commentBody;
  String handleCreator;

}

package com.kdbf.forum.adapters.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kdbf.forum.adapters.out.persistence.entity.TopicJpa;

@Repository
public interface TopicRepository extends JpaRepository<Long, TopicJpa> {

  public TopicJpa findByTitleAndCourseName(String title, String courseName);

}

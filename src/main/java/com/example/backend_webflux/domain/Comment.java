package com.example.backend_webflux.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
public class Comment {

  @Id
  private Integer id;
  private String content;


  private String user;

  private String post;
}

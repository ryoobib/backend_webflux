package com.example.backend_webflux.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
public class Post {

  @Id
  private String id;
  private String title;
  private String content;
  private Integer status;
  @DBRef
  private User user;

}

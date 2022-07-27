package com.example.backend_webflux.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document
public class Scrap {

  @Id
  private String id;
  private Integer status;

  @DBRef
  private Post post;
  @DBRef
  private User user;


}

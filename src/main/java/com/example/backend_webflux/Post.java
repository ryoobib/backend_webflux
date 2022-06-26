package com.example.backend_webflux;

import java.sql.Timestamp;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Post {

  @Id
  private Integer id;
  private String title;
  private String content;
  private Integer status;
  private Timestamp createdAt;

}

package com.example.backend_webflux;

import java.sql.Timestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Comment {

  @Id
  private Integer id;
  private String content;

  @CreatedDate
  private Timestamp createdAt;
}

package com.example.backend_webflux;

import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
public class Post {

  @Id
  private Integer id;
  private String title;
  private String content;
  private Integer status;

  @CreatedDate
  private Timestamp createdAt;

}

package com.example.backend_webflux.domain;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {

  @Id
  private String id;
  private String title;
  private String content;
  private Integer status;

  @CreatedDate
  private Timestamp createdAt;

  private String userId;

}

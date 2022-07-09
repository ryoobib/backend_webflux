package com.example.backend_webflux.domain;

import java.sql.Timestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Scrap {

  @Id
  private Integer id;
  private Integer status;

  @CreatedDate
  private Timestamp createdAt;

}

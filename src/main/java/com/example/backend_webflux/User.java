package com.example.backend_webflux;

import java.sql.Timestamp;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {

  @Id
  private Integer id;
  private String name;
  private Timestamp createdAt;
}

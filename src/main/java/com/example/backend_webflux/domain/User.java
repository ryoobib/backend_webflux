package com.example.backend_webflux.domain;

import java.sql.Timestamp;
import java.util.Set;
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

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document
public class User {

  @Id
  private String id;
  private String name;

  @CreatedDate
  private Timestamp createdAt;

//  @ReadOnlyProperty
//  @DocumentReference(lookup="{'userId':?#{#self._id} }")
//  private Set<Post> posts; // NPE
}

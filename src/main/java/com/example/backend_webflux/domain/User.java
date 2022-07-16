package com.example.backend_webflux.domain;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

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
  private Date createdAt;

  @Version
  private Integer version;

//  @ReadOnlyProperty
//  @DocumentReference(lookup="{'userId':?#{#self._id} }")
//  private Set<Post> posts; // NPE
}

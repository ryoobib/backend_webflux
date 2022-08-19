package com.example.backend_webflux.domain;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Token {
  private String userId;
  private String token;
  private Date issuedAt;
  private Date expiresAt;
}

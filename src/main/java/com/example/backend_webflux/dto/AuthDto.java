package com.example.backend_webflux.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


public class AuthDto {

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @ToString
  public static class Request {
    private String name;
    private String password;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Response {
    private String userId;
    private String token;
    private Date issuedAt;
    private Date expiresAt;
  }
}

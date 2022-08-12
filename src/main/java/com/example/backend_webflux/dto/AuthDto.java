package com.example.backend_webflux.dto;

import lombok.AllArgsConstructor;
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
  @ToString
  public static class Response {
    private String token;
  }
}

package com.example.backend_webflux.dto;

import lombok.Data;

@Data
public class CommentDto {

  private String content;
  private String postId;
  private String userId;
}

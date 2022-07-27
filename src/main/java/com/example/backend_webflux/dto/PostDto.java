package com.example.backend_webflux.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
public class PostDto {

    private String title;
    private String content;
    private String userId;

}

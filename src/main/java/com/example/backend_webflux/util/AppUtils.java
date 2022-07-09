package com.example.backend_webflux.util;

import com.example.backend_webflux.domain.Post;
import com.example.backend_webflux.dto.PostDto;
import org.springframework.beans.BeanUtils;

public class AppUtils {
  public static PostDto entityToDto(Post entity) {
    PostDto dto = new PostDto();
    BeanUtils.copyProperties(entity, dto);
    return dto;
  }

  public static Post dtoToEntity(PostDto dto) {
    Post entity = new Post();
    BeanUtils.copyProperties(dto, entity);
    return entity;
  }
}

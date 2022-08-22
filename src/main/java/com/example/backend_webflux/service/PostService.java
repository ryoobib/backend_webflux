package com.example.backend_webflux.service;

import com.example.backend_webflux.domain.Post;
import com.example.backend_webflux.dto.PostDto;
import com.example.backend_webflux.exception.ApiException;
import com.example.backend_webflux.exception.ApiExceptionEnum;
import com.example.backend_webflux.repository.PostRepository;
import com.example.backend_webflux.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class PostService {

  private final PostRepository postRepository;
  private final UserRepository userRepository;

  public Flux<Post> getAllPostFilterTitle(String title, Pageable pageable) {
    return postRepository.findByTitleContains(title, pageable);
  }

  public Flux<Post> getAllPost() {
    return postRepository.findAll()
        .switchIfEmpty(Mono.error(new ApiException(ApiExceptionEnum.NOT_FOUND_EXCEPTION)));
  }

  public Mono<Post> getPost(String id) {
    return postRepository.findById(id)
        .switchIfEmpty(Mono.error(new ApiException(ApiExceptionEnum.NOT_FOUND_EXCEPTION)));
  }

  public Mono<Post> modify(String id, String userId, PostDto dto) {
    return postRepository.findById(id)
        .switchIfEmpty(Mono.error(new ApiException(ApiExceptionEnum.NOT_FOUND_EXCEPTION)))
        .flatMap(result -> {
          if (result.getUser().equals(userId)) {
            result.setTitle(dto.getTitle());
            result.setContent(dto.getContent());
            result.setStatus(1);
            return postRepository.save(result);
          } else {
           return Mono.error(new ApiException(ApiExceptionEnum.WRITER_UNAUTHORIZED_EXCEPTION));
          }
        });
  }

  public Mono<Void> delete(String id, String userId) {
    return userRepository.findById(userId)
        .flatMap(writer -> postRepository.findById(id).flatMap(post -> {
          if( writer.getId().equals(post.getUser())) {
            return postRepository.deleteById(id);
          }
          return Mono.empty();
        })).switchIfEmpty(Mono.error(new ApiException(ApiExceptionEnum.WRITER_UNAUTHORIZED_EXCEPTION)));
  }

  public Mono<Post> create(String userId, PostDto dto) {
    return userRepository.findById(userId)
        .switchIfEmpty(Mono.error(new ApiException(ApiExceptionEnum.NOT_FOUND_EXCEPTION)))
        .flatMap(user -> {
          Post p = new Post();
          BeanUtils.copyProperties(dto, p);
          p.setStatus(0);
          p.setUser(user.getId());
          return postRepository.insert(p);
        });
  }
}

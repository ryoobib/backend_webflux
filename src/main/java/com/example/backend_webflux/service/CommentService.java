package com.example.backend_webflux.service;

import com.example.backend_webflux.domain.Comment;
import com.example.backend_webflux.dto.CommentDto;
import com.example.backend_webflux.exception.ApiException;
import com.example.backend_webflux.exception.ApiExceptionEnum;
import com.example.backend_webflux.repository.CommentRepository;
import com.example.backend_webflux.repository.PostRepository;
import com.example.backend_webflux.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class CommentService {

  private final CommentRepository commentRepository;
  private final UserRepository userRepository;
  private final PostRepository postRepository;

  public Flux<Comment> getAllComment() {
    return commentRepository.findAll();
  }

  public Mono<Comment> create(CommentDto dto) {
    return userRepository.findById(dto.getUserId())
        .switchIfEmpty(Mono.error(new ApiException(ApiExceptionEnum.USER_NOT_FOUND_EXCEPTION)))
        .flatMap(user -> postRepository.findById(dto.getPostId()))
        .switchIfEmpty(Mono.error(new ApiException(ApiExceptionEnum.POST_NOT_FOUND_EXCEPTION)))
        .flatMap(post -> {
          Comment c = new Comment();
          c.setContent(dto.getContent());
          c.setPost(post.getId());
          c.setUser(dto.getUserId());
          return commentRepository.insert(c);
        });
  }

  public Mono<Comment> modify(String id, CommentDto dto) {
    return commentRepository.findById(id)
        .switchIfEmpty(Mono.error(new ApiException(ApiExceptionEnum.NOT_FOUND_EXCEPTION)))
        .flatMap(result -> {
          result.setContent(dto.getContent());
          return commentRepository.save(result);
        });
  }

  public Mono<Void> delete(String id) {
    return commentRepository.deleteById(id);
  }
}

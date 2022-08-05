package com.example.backend_webflux.handler;

import com.example.backend_webflux.domain.Post;
import com.example.backend_webflux.dto.PostDto;
import com.example.backend_webflux.service.PostService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class PostHandler {

  private final PostService postService;


  public PostHandler(PostService postService) {
    this.postService = postService;
  }


  public Mono<ServerResponse> getPost(ServerRequest request) {
    String id = request.pathVariable("id");

    Mono<Post> post = postService.getPost(id);

    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(post, Post.class);
  }

  public Mono<ServerResponse> modifyPost(ServerRequest request) {
    Mono<PostDto> unsavedPost = request.bodyToMono(PostDto.class);
    String id = request.pathVariable("id");

    Mono<Post> updatedPost = unsavedPost.flatMap(dto -> postService.modify(id, dto));

    return ServerResponse.accepted()
        .contentType(MediaType.APPLICATION_JSON)
        .body(updatedPost, Post.class);
  }

  public Mono<ServerResponse> deletePost(ServerRequest request) {
    String id = request.pathVariable("id");

    Mono<Void> deleted = postService.delete(id);

    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(deleted, Void.class);
  }

  public Mono<ServerResponse> getAllPostsPaging(ServerRequest request) {
    Pageable pageable = PageRequest.of(0,3);

    Flux<Post> pages = request
        .queryParam("title")
        .map(title -> postService.getAllPostFilterTitle(title, pageable))
        .orElseGet(postService::getAllPost);

    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(pages, Post.class);
  }


  public Mono<ServerResponse> createPostByUserId(ServerRequest request) {

    Mono<PostDto> dto = request.bodyToMono(PostDto.class);
    Mono<Post> post = dto.flatMap(postService::create);

    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(post, Post.class);
  }
}

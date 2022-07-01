package com.example.backend_webflux;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class PostHandler {

  private final PostRepository postRepository;


  public PostHandler(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  public Mono<ServerResponse> getAllPosts(ServerRequest request) {
    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(postRepository.findAll(), Post.class);
  }

  public Mono<ServerResponse> getPost(ServerRequest request) {
    Integer id = Integer.valueOf(request.pathVariable("id"));

    return postRepository.findById(id)
        .flatMap(post -> ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(fromValue(post))
        ).switchIfEmpty(ServerResponse.notFound().build());
  }

  public Mono<ServerResponse> createPost(ServerRequest request) {
    Mono<Post> unsavedPost = request.bodyToMono(Post.class);
    return unsavedPost
        .flatMap(post -> postRepository.save(post)
            .flatMap(savedPost -> ServerResponse.accepted()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(savedPost)))
        ).switchIfEmpty(ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).build());
  }

  public Mono<ServerResponse> modifyPost(ServerRequest request) {
    Mono<Post> unsavedPost = request.bodyToMono(Post.class);
    Integer id = Integer.valueOf(request.pathVariable("id"));

    Mono<Post> updatedPost = unsavedPost.flatMap(post ->
        postRepository.findById(id)
            .flatMap(oldPost -> {
              post.setId(id);
              return postRepository.save(post);
            }));

    return updatedPost.flatMap(post ->
        ServerResponse.accepted()
            .contentType(MediaType.APPLICATION_JSON)
            .body(fromValue(post))
    ).switchIfEmpty(ServerResponse.notFound().build());
  }

  public Mono<ServerResponse> deletePost(ServerRequest request) {
    Integer id = Integer.valueOf(request.pathVariable("id"));

    Mono<Void> deleted = postRepository.deleteById(id);

    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(deleted, Void.class);
  }

  public Mono<ServerResponse> getAllPostsPaging(ServerRequest request) {
    Pageable pageable = PageRequest.of(0,3);

    Flux<Post> pages = request
        .queryParam("title")
        .map(title -> postRepository.findByTitleContains(title, pageable))
        .orElseGet(() -> postRepository.findAll());

    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(pages, Post.class);
  }
}

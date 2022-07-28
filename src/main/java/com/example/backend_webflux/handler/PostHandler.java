package com.example.backend_webflux.handler;

import com.example.backend_webflux.domain.Post;
import com.example.backend_webflux.dto.PostDto;
import com.example.backend_webflux.repository.PostRepository;
import com.example.backend_webflux.repository.UserRepository;
import org.springframework.beans.BeanUtils;
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

  private final PostRepository postRepository;
  private final UserRepository userRepository;


  public PostHandler(PostRepository postRepository,
      UserRepository userRepository) {
    this.postRepository = postRepository;
    this.userRepository = userRepository;
  }

  public Mono<ServerResponse> getAllPosts(ServerRequest request) {
    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(postRepository.findAll(), Post.class);
  }

  public Mono<ServerResponse> getPost(ServerRequest request) {
    String id = request.pathVariable("id");

    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(postRepository.findById(id), Post.class);
  }

//  public Mono<ServerResponse> createPost(ServerRequest request) {
//    Mono<Post> unsavedPost = request.bodyToMono(Post.class);
//    return unsavedPost
//        .flatMap(post -> postRepository.save(post)
//            .flatMap(savedPost -> ServerResponse.accepted()
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(fromValue(savedPost)))
//        ).switchIfEmpty(ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).build());
//  }

  public Mono<ServerResponse> modifyPost(ServerRequest request) {
    Mono<Post> unsavedPost = request.bodyToMono(Post.class);
    String id = request.pathVariable("id");

    Mono<Post> updatedPost = unsavedPost.flatMap(post ->
        postRepository.findById(id)
            .flatMap(oldPost -> {
              post.setId(id);
              post.setUser(oldPost.getUser());
              post.setStatus(1);
              return postRepository.save(post);
            }));

    return ServerResponse.accepted()
        .contentType(MediaType.APPLICATION_JSON)
        .body(updatedPost, Post.class);
  }

  public Mono<ServerResponse> deletePost(ServerRequest request) {
    String id = request.pathVariable("id");

    Mono<Void> deleted = postRepository.deleteById(id);

    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(deleted, Void.class);
  }

  public Mono<ServerResponse> getAllPostsPaging(ServerRequest request) {
    Pageable pageable = PageRequest.of(0,3);

    Flux<Post> pages = request
        .queryParam("title")
        .map(title -> postRepository.findByTitleContains(title, pageable))
        .orElseGet(postRepository::findAll);

    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(pages, Post.class);
  }


  public Mono<ServerResponse> createPostByUserId(ServerRequest request) {

    Mono<PostDto> dto = request.bodyToMono(PostDto.class);
    Mono<Post> post =
        dto.flatMap(d ->
          userRepository.findById(d.getUserId())
              .flatMap(user -> {
                Post p = new Post();
                BeanUtils.copyProperties(d, p);
                p.setStatus(0);
                p.setUser(user.getId());
                return postRepository.insert(p);
              })
        );


//        userRepository.findById(userId)
//            .flatMap(u -> dto.map(AppUtils::dtoToEntity)
//            .doOnNext(e -> e.setUser(u))
//        ).flatMap(postRepository::insert);
    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(post, Post.class);
  }
}

package com.example.backend_webflux.handler;

import com.example.backend_webflux.domain.Comment;
import com.example.backend_webflux.dto.CommentDto;
import com.example.backend_webflux.repository.CommentRepository;
import com.example.backend_webflux.repository.PostRepository;
import com.example.backend_webflux.repository.UserRepository;
import exception.ExceptionResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.webjars.NotFoundException;
import reactor.core.publisher.Mono;

@Component
public class CommentHandler {

  private final CommentRepository commentRepository;
  private final UserRepository userRepository;
  private final PostRepository postRepository;

  public CommentHandler(CommentRepository commentRepository,
      UserRepository userRepository,
      PostRepository postRepository) {
    this.commentRepository = commentRepository;
    this.userRepository = userRepository;
    this.postRepository = postRepository;
  }

  public Mono<ServerResponse> getAllComment(ServerRequest request) {
    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(commentRepository.findAll(), Comment.class);

  }

  public Mono<ServerResponse> createComment(ServerRequest request) {
    Mono<CommentDto> dto = request.bodyToMono(CommentDto.class);

    Mono<Comment> comment =
        dto.flatMap(d -> userRepository.findById(d.getUserId())
            .flatMap(user -> postRepository.findById(d.getPostId())
                .flatMap(post -> {
                  Comment c = new Comment();
                  c.setContent(d.getContent());
                  c.setPost(post.getId());
                  c.setUser(user.getId());
                  return commentRepository.insert(c);
                })));
    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(comment, Comment.class);
  }

  public Mono<ServerResponse> modifyComment(ServerRequest request) {
    String commentId = request.pathVariable("id");
    Mono<CommentDto> dto = request.bodyToMono(CommentDto.class);

    Mono<Comment> updatedComment =
        dto.flatMap(d -> commentRepository.findById(commentId)
            .switchIfEmpty(Mono.error(new NotFoundException("comment not found with id :: " + commentId)))
            .flatMap(comment -> {
              comment.setContent(d.getContent());
              return commentRepository.save(comment);
            }));
    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(updatedComment, Comment.class);
  }

  public Mono<ServerResponse> deleteComment(ServerRequest request) {
    String commentId = request.pathVariable("id");
    Mono<Void> deleted = commentRepository.deleteById(commentId);

    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(deleted, Void.class);
  }
}

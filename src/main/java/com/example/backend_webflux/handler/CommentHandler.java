package com.example.backend_webflux.handler;

import com.example.backend_webflux.domain.Comment;
import com.example.backend_webflux.dto.CommentDto;
import com.example.backend_webflux.service.CommentService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.webjars.NotFoundException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CommentHandler {

  private final CommentService commentService;

  public CommentHandler(CommentService commentService) {
    this.commentService = commentService;
  }

  public Mono<ServerResponse> getAllComment(ServerRequest request) {

    Flux<Comment> comments = commentService.getAllComment();

    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(comments, Comment.class);

  }

  public Mono<ServerResponse> createComment(ServerRequest request) {
    Mono<CommentDto> dto = request.bodyToMono(CommentDto.class);

    Mono<Comment> comment =
        dto.flatMap(commentService::create);

    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(comment, Comment.class);
  }

  public Mono<ServerResponse> modifyComment(ServerRequest request) {
    String commentId = request.pathVariable("id");
    Mono<CommentDto> dto = request.bodyToMono(CommentDto.class);

    Mono<Comment> updatedComment =
        dto.flatMap(d -> commentService.modify(commentId, d));

    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(updatedComment, Comment.class);
  }

  public Mono<ServerResponse> deleteComment(ServerRequest request) {
    String commentId = request.pathVariable("id");
    Mono<Void> deleted = commentService.delete(commentId);

    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(deleted, Void.class);
  }
}

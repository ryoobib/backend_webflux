package com.example.backend_webflux.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;

import com.example.backend_webflux.annotation.comment.CreateCommentApiInfo;
import com.example.backend_webflux.annotation.comment.DeleteCommentApiInfo;
import com.example.backend_webflux.annotation.comment.GetAllCommentApiInfo;
import com.example.backend_webflux.annotation.comment.ModifyCommentApiInfo;
import com.example.backend_webflux.handler.CommentHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class CommentRouter {

  @Bean
  @GetAllCommentApiInfo
  public RouterFunction<ServerResponse> getAllComment(CommentHandler commentHandler) {
    return RouterFunctions
        .route(GET("/api/comment")
        , commentHandler::getAllComment);
  }

  @Bean
  @CreateCommentApiInfo
  public RouterFunction<ServerResponse> createComment(CommentHandler commentHandler) {
    return RouterFunctions
        .route(POST("/api/comment")
        , commentHandler::createComment);
  }

  @Bean
  @ModifyCommentApiInfo
  public RouterFunction<ServerResponse> modifyComment(CommentHandler commentHandler) {
    return RouterFunctions
        .route(PUT("/api/comment/{id}")
            , commentHandler::modifyComment);
  }

  @Bean
  @DeleteCommentApiInfo
  public RouterFunction<ServerResponse> deleteComment(CommentHandler commentHandler) {
    return RouterFunctions
        .route(DELETE("/api/comment/{id}")
            , commentHandler::deleteComment);
  }
}

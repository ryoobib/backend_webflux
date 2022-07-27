package com.example.backend_webflux.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;

import com.example.backend_webflux.annotation.post.CreatePostApiInfo;
import com.example.backend_webflux.annotation.post.CreatePostByUserIdApiInfo;
import com.example.backend_webflux.annotation.post.DeletePostByIdApiInfo;
import com.example.backend_webflux.annotation.post.GetAllPostApiInfo;
import com.example.backend_webflux.annotation.post.GetPostByIdApiInfo;
import com.example.backend_webflux.annotation.post.GetPostByUserIdApiInfo;
import com.example.backend_webflux.annotation.post.ModifyPostByIdApiInfo;
import com.example.backend_webflux.handler.PostHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class PostRouter {

  @Bean
  @GetAllPostApiInfo
  public RouterFunction<ServerResponse> getAllPostRouter(PostHandler postHandler) {
    return RouterFunctions
        .route(GET("/api/post")
            , postHandler::getAllPostsPaging);
  }

  @Bean
  @GetPostByIdApiInfo
  public RouterFunction<ServerResponse> getPostByIdRouter(PostHandler postHandler) {
    return RouterFunctions
        .route(GET("/api/post/{id}")
            , postHandler::getPost);
  }

//  @Bean
//  @GetPostByUserIdApiInfo
//  public RouterFunction<ServerResponse> getPostByUserIdRouter(PostHandler postHandler) {
//    return RouterFunctions
//        .route(GET("/api/post/{userId}/user")
//            , postHandler::getUserPosts);
//  }

//  @Bean
//  @CreatePostApiInfo
//  public RouterFunction<ServerResponse> createPostRouter(PostHandler postHandler) {
//    return RouterFunctions
//        .route(POST("/api/post")
//            , postHandler::createPost);
//  }

  @Bean
  @CreatePostByUserIdApiInfo
  public RouterFunction<ServerResponse> createPostByUserIdRouter(PostHandler postHandler) {
    return RouterFunctions
        .route(POST("/api/post/{userId}/user")
            , postHandler::createPostByUserId);
  }

  @Bean
  @ModifyPostByIdApiInfo
  public RouterFunction<ServerResponse> modifyPostByIdRouter(PostHandler postHandler) {
    return RouterFunctions
        .route(PUT("/api/post/{id}")
            , postHandler::modifyPost);
  }

  @Bean
  @DeletePostByIdApiInfo
  public RouterFunction<ServerResponse> deletePostByIdRouter(PostHandler postHandler) {
    return RouterFunctions
        .route(DELETE("/api/post/{id}")
            , postHandler::deletePost);
  }
}

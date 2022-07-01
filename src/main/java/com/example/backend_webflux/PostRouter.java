package com.example.backend_webflux;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class PostRouter {

  @Bean
  public RouterFunction<ServerResponse> routePost(PostHandler postHandler) {
    return RouterFunctions
        .route(GET("/api/post")
            , postHandler::getAllPostsPaging)
        .andRoute(GET("/api/post/{id}")
            , postHandler::getPost)
        .andRoute(POST("/api/post")
            , postHandler::createPost)
        .andRoute(PUT("/api/post/{id}")
            , postHandler::modifyPost)
        .andRoute(DELETE("/api/post/{id}")
            , postHandler::deletePost);
  }
}

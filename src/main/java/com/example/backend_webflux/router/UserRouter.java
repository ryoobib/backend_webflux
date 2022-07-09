package com.example.backend_webflux.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;

import com.example.backend_webflux.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class UserRouter {

  @Bean
  public RouterFunction<ServerResponse> routeUser(UserHandler userHandler) {
    return RouterFunctions
        .route(GET("/api/user")
        , userHandler::getAllUser)
        .andRoute(GET("/api/user/{id}")
        , userHandler::getUser)
        .andRoute(POST("api/user")
        , userHandler::createUser)
        .andRoute(PUT("api/user/{id}")
        , userHandler::modifyUser)
        .andRoute(DELETE("api/user/{id}")
        , userHandler::deleteUser);
  }
}

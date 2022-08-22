package com.example.backend_webflux.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;

import com.example.backend_webflux.annotation.user.CreateUserApiInfo;
import com.example.backend_webflux.annotation.user.LoginApiInfo;
import com.example.backend_webflux.handler.AuthHandler;
import com.example.backend_webflux.handler.UserHandler;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class AuthRouter {

  @Bean
  @CreateUserApiInfo
  public RouterFunction<ServerResponse> createUserRouter(UserHandler userHandler) {
    return RouterFunctions
        .route(POST("/api/user/register")
            , userHandler::createUser);
  }
  @Bean
  @LoginApiInfo
  public RouterFunction<ServerResponse> login(AuthHandler authHandler) {
    return RouterFunctions
        .route(POST("/api/user/login")
            , authHandler::login);
  }

  @Bean
  public RouterFunction<ServerResponse> modifyPwRouter(AuthHandler authHandler) {
    return RouterFunctions
        .route(PUT("/api/user/pw")
            , authHandler::modifyPw);
  }
}

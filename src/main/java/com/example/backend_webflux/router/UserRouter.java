package com.example.backend_webflux.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;

import com.example.backend_webflux.annotation.user.DeleteUserByIdApiInfo;
import com.example.backend_webflux.annotation.user.GetUserByIdApiInfo;
import com.example.backend_webflux.annotation.user.ModifyUserByIdApiInfo;
import com.example.backend_webflux.handler.UserHandler;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class UserRouter {

  @Bean
  public WebProperties.Resources resources(){
    return new WebProperties.Resources();
  }


  @Bean
  @GetUserByIdApiInfo
  public RouterFunction<ServerResponse> getUserRouter(UserHandler userHandler) {
    return RouterFunctions
        .route(GET("/api/user")
            , userHandler::getUser);
  }


  @Bean
  @ModifyUserByIdApiInfo
  public RouterFunction<ServerResponse> modifyUserRouter(UserHandler userHandler) {
    return RouterFunctions
        .route(PUT("/api/user")
            , userHandler::modifyUser);
  }

  @Bean
  @DeleteUserByIdApiInfo
  public RouterFunction<ServerResponse> deleteUserRouter(UserHandler userHandler) {
    return RouterFunctions
        .route(DELETE("/api/user")
            , userHandler::deleteUser);
  }

}

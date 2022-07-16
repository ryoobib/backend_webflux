package com.example.backend_webflux.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ScrabRouter {

  @Bean
  public RouterFunction<ServerResponse> getAllScrabRouter(ScrabHandler scrabHandler) {
    return RouterFunctions
        .route(GET("/api/scrab")
        , scrabHandler::getAllScrab)
        ;
  }
}

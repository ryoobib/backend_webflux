package com.example.backend_webflux.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;

import com.example.backend_webflux.handler.ScrapHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ScrapRouter {

  @Bean
  public RouterFunction<ServerResponse> getAllScrapRouter(ScrapHandler scrapHandler) {
    return RouterFunctions
        .route(GET("/api/scrap")
        , scrapHandler::getAllScrap)
        ;
  }

  @Bean
  public RouterFunction<ServerResponse> getScrapByUserId(ScrapHandler scrapHandler) {
    return RouterFunctions
        .route(GET("/api/scrap/{userId}")
            , scrapHandler::getScrapByUserId)
        ;
  }

  @Bean
  public RouterFunction<ServerResponse> createScrap(ScrapHandler scrapHandler) {
    return RouterFunctions
        .route(POST("/api/scrap")
            , scrapHandler::createScrap)
        ;
  }

  @Bean
  public RouterFunction<ServerResponse> modifyScrap(ScrapHandler scrapHandler) {
    return RouterFunctions
        .route(PUT("/api/scrap/{scrapId}")
            , scrapHandler::modifyScrap)
        ;
  }
}

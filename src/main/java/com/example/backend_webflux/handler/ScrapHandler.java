package com.example.backend_webflux.handler;

import com.example.backend_webflux.domain.Scrap;
import com.example.backend_webflux.dto.ScrapDto;
import com.example.backend_webflux.service.ScrapService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ScrapHandler {

  private final ScrapService scrapService;

  public ScrapHandler(ScrapService scrapService) {
    this.scrapService = scrapService;
  }

  public Mono<ServerResponse> getAllScrap(ServerRequest request) {
    Flux<Scrap> scraps = scrapService.getAll();

    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(scraps, Scrap.class);
  }

  public Mono<ServerResponse> getScrapByUserId(ServerRequest request) {
    String userId = request.pathVariable("userId");

    Flux<Scrap> scraps = scrapService.getAllByUserId(userId);

    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(scraps, Scrap.class)
        .switchIfEmpty(ServerResponse.notFound().build());
  }

  public Mono<ServerResponse> createScrap(ServerRequest request) {
    Mono<ScrapDto> dto = request.bodyToMono(ScrapDto.class);

    Mono<Scrap> scrap = dto.flatMap(scrapService::create);

    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(scrap, Scrap.class);
  }

  public Mono<ServerResponse> modifyScrap(ServerRequest request) {
    String scrapId = request.pathVariable("scrapId");

    Mono<Scrap> updatedScrap = scrapService.modify(scrapId);

    return ServerResponse.accepted()
        .contentType(MediaType.APPLICATION_JSON)
        .body(updatedScrap, Scrap.class);
  }
}

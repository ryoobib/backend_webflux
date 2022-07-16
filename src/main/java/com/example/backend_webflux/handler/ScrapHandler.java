package com.example.backend_webflux.handler;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

import com.example.backend_webflux.domain.Scrap;
import com.example.backend_webflux.repository.PostRepository;
import com.example.backend_webflux.repository.ScrapRepository;
import com.example.backend_webflux.repository.UserRepository;
import exception.ExceptionResponse;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ScrapHandler {

  private final ScrapRepository scrapRepository;
  private final UserRepository userRepository;
  private final PostRepository postRepository;


  public ScrapHandler(ScrapRepository scrapRepository,
      UserRepository userRepository,
      PostRepository postRepository) {
    this.scrapRepository = scrapRepository;
    this.userRepository = userRepository;
    this.postRepository = postRepository;
  }

  public Mono<ServerResponse> getAllScrap(ServerRequest request) {
    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(scrapRepository.findAll(), Scrap.class);
  }

  public Mono<ServerResponse> getScrapByUserId(ServerRequest request) {
    String userId = request.pathVariable("userId");

    Flux<Scrap> scraps = userRepository.findById(userId)
        .flatMapMany(u -> scrapRepository.findByUserId(u.getId()))
        ;

    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(scraps, Scrap.class)
        .switchIfEmpty(ServerResponse.notFound().build());
  }

  public Mono<ServerResponse> createScrap(ServerRequest request) {
    String userId = request.queryParam("userId").orElse("");
    String postId = request.queryParam("postId").orElse("");

    Mono<Scrap> scrap = userRepository.findById(userId)
        .map(u -> Scrap.builder().userId(u.getId()).status(0).build())
        .doOnNext(s -> postRepository.findById(postId)
            .filter(p -> !p.getUserId().equals(userId))
            .doOnNext(p -> s.setPostId(p.getId())) // null
        ).flatMap(scrapRepository::save);
    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(scrap, Scrap.class);
  }

  public Mono<ServerResponse> modifyScrap(ServerRequest request) {
    String scrapId = request.pathVariable("scrapId");

    Mono<Scrap> updatedScrap = scrapRepository.findById(scrapId)
        .flatMap(s -> {
          s.setStatus(1);
          return scrapRepository.save(s);
        });
    return updatedScrap.flatMap(scrap ->
        ServerResponse.accepted()
            .contentType(MediaType.APPLICATION_JSON)
            .body(fromValue(scrap))
    ).switchIfEmpty(ServerResponse.notFound().build());
  }
}

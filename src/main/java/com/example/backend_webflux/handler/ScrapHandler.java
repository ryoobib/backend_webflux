package com.example.backend_webflux.handler;

import com.example.backend_webflux.domain.Scrap;
import com.example.backend_webflux.dto.ScrapDto;
import com.example.backend_webflux.repository.PostRepository;
import com.example.backend_webflux.repository.ScrapRepository;
import com.example.backend_webflux.repository.UserRepository;
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
        .flatMapMany(scrapRepository::findByUser)
        ;

    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(scraps, Scrap.class)
        .switchIfEmpty(ServerResponse.notFound().build());
  }

  public Mono<ServerResponse> createScrap(ServerRequest request) {

    Mono<ScrapDto> dto = request.bodyToMono(ScrapDto.class);

    Mono<Scrap> scrap =
        dto.flatMap(d1 -> userRepository.findById(d1.getUserId())
            .flatMap(user -> postRepository.findById(d1.getPostId())
                .flatMap(post -> {
                  Scrap s = new Scrap();
                  s.setUser(user);
                  s.setStatus(0);
                  s.setPost(post);
                  return scrapRepository.insert(s);
                })
            )
        );


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
    return ServerResponse.accepted()
        .contentType(MediaType.APPLICATION_JSON)
        .body(updatedScrap, Scrap.class);
  }
}

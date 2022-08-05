package com.example.backend_webflux.service;

import com.example.backend_webflux.domain.Scrap;
import com.example.backend_webflux.dto.ScrapDto;
import com.example.backend_webflux.exception.ApiException;
import com.example.backend_webflux.exception.ApiExceptionEnum;
import com.example.backend_webflux.repository.PostRepository;
import com.example.backend_webflux.repository.ScrapRepository;
import com.example.backend_webflux.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class ScrapService {

  private final ScrapRepository scrapRepository;
  private final PostRepository postRepository;
  private final UserRepository userRepository;

  public Flux<Scrap> getAll() {
    return scrapRepository.findAll();
  }

  public Flux<Scrap> getAllByUserId(String userId) {
    return userRepository.findById(userId)
        .switchIfEmpty(Mono.error(new ApiException(ApiExceptionEnum.USER_NOT_FOUND_EXCEPTION)))
        .flatMapMany(user -> scrapRepository.findByUser(user.getId()))
        .switchIfEmpty(Mono.error(new ApiException(ApiExceptionEnum.NOT_FOUND_EXCEPTION)));
  }

  public Mono<Scrap> create(ScrapDto dto) {
    return userRepository.findById(dto.getUserId())
        .switchIfEmpty(Mono.error(new ApiException(ApiExceptionEnum.USER_NOT_FOUND_EXCEPTION)))
        .flatMap(user -> postRepository.findById(dto.getPostId())
            .switchIfEmpty(Mono.error(new ApiException(ApiExceptionEnum.POST_NOT_FOUND_EXCEPTION)))
            .flatMap(post -> scrapRepository.findByPost(post.getId())
                .switchIfEmpty(Mono.defer(() -> {
                  Scrap s = new Scrap();
                  s.setUser(user.getId());
                  s.setPost(post.getId());
                  s.setStatus(0);
                  return scrapRepository.insert(s);
                }))
                .flatMap(scrap -> Mono.error(new ApiException(ApiExceptionEnum.DUPLICATION_VALUE_EXCEPTION)))
            )
        );
  }

  public Mono<Scrap> modify(String id) {
    return scrapRepository.findById(id)
        .switchIfEmpty(Mono.error(new ApiException(ApiExceptionEnum.NOT_FOUND_EXCEPTION)))
        .flatMap(scrap -> {
          scrap.setStatus(1);
          return scrapRepository.save(scrap);
        });
  }
}

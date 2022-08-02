package com.example.backend_webflux.service;

import com.example.backend_webflux.domain.User;
import com.example.backend_webflux.exception.ApiException;
import com.example.backend_webflux.exception.ApiExceptionEnum;
import com.example.backend_webflux.repository.UserRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class UserService {

  private final UserRepository userRepository;


  public Mono<User> create(User user) {
    return userRepository.findByName(user.getName())
        .defaultIfEmpty(user)
        .flatMap(result -> {
          if (result.getId() == null) {
            return userRepository.save(result);
          } else {
            return Mono.error(new ApiException(ApiExceptionEnum.DUPLICATION_VALUE_EXCEPTION));
          }
        });
  }

  public Mono<User> getUserById(String id) {
    return userRepository.findById(id)
        .switchIfEmpty(Mono.error(new ApiException(ApiExceptionEnum.NOT_FOUND_EXCEPTION)));
  }

  public Flux<User> getAllUser() {
    return userRepository.findAll()
        .switchIfEmpty(Mono.error(new ApiException(ApiExceptionEnum.NOT_FOUND_EXCEPTION)));
  }

  public Mono<User> modify(String id, User user) {
    return userRepository.findById(id)
        .switchIfEmpty(Mono.error(new ApiException(ApiExceptionEnum.NOT_FOUND_EXCEPTION)))
        .flatMap(u -> {
              u.setName(user.getName());
              return userRepository.save(u);
            })
        ;

  }

  public Mono<Void> delete(String id) {
    return userRepository.deleteById(id);
  }
}

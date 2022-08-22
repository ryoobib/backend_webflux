package com.example.backend_webflux.handler;

import com.example.backend_webflux.domain.User;
import com.example.backend_webflux.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserHandler {

  private final UserService userService;


  @PreAuthorize("hasRole('USER')")
  public Mono<ServerResponse> getUser(ServerRequest request) {

    Mono<User> user = request.principal()
        .flatMap(auth -> userService.getUserById(auth.getName()));

    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(user, User.class);
  }

  public Mono<ServerResponse> createUser(ServerRequest request) {
    Mono<User> unsavedUser = request.bodyToMono(User.class);

    Mono<User> saved = unsavedUser.flatMap(userService::create);

    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(saved, User.class);
  }

  public Mono<ServerResponse> modifyUser(ServerRequest request) {

    Mono<User> updatedUser = Mono.zip(request.bodyToMono(User.class), request.principal())
        .flatMap(z -> userService.modify(z.getT2().getName(), z.getT1()));

    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(updatedUser, User.class);
  }

  public Mono<ServerResponse> deleteUser(ServerRequest request) {
    Mono<Void> deleted = request.principal()
        .flatMap(auth -> userService.delete(auth.getName()));

    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(deleted, Void.class);
  }

}

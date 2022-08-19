package com.example.backend_webflux.handler;

import com.example.backend_webflux.domain.User;
import com.example.backend_webflux.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserHandler {

  private final UserService userService;

  public Mono<ServerResponse> getAllUser(ServerRequest request) {
    Flux<User> users = userService.getAllUser();

    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(users, User.class);
  }

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
    Mono<User> unsavedUser = request.bodyToMono(User.class);
    String id = request.pathVariable("id");

    Mono<User> updatedUser = unsavedUser.flatMap(u -> userService.modify(id, u));

    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(updatedUser, User.class);
  }

  public Mono<ServerResponse> deleteUser(ServerRequest request) {
    String id = request.pathVariable("id");
    Mono<Void> deleted = userService.delete(id);

    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(deleted, Void.class);
  }

  public Mono<ServerResponse> modifyPw(ServerRequest request) {



    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(null, User.class);
  }
}

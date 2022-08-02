package com.example.backend_webflux.handler;

import com.example.backend_webflux.domain.User;
import com.example.backend_webflux.exception.ApiException;
import com.example.backend_webflux.exception.ApiExceptionEnum;
import com.example.backend_webflux.repository.UserRepository;
import com.example.backend_webflux.service.UserService;
import com.mongodb.internal.connection.Server;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class UserHandler {

  private final UserService userService;

  public UserHandler(UserService userService) {
    this.userService = userService;
  }


  public Mono<ServerResponse> getAllUser(ServerRequest request) {
    Flux<User> users = userService.getAllUser();

    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(users, User.class);
  }

  public Mono<ServerResponse> getUser(ServerRequest request) {
    String id = request.pathVariable("id");

    Mono<User> user = userService.getUserById(id);

    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(user, User.class);
  }

  public Mono<ServerResponse> createUser(ServerRequest request) {
    Mono<User> unsavedUser = request.bodyToMono(User.class);

    Mono<User> saved = unsavedUser.flatMap(userService::create);

    return saved.flatMap(data -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(data))
        .switchIfEmpty(ServerResponse.notFound().build())
        .onErrorResume(error -> {
          if(error instanceof ApiException) {
            return ServerResponse.status(((ApiException) error).getError().getStatus()).bodyValue(((ApiException) error).getError().getMessage());
          }
          return ServerResponse.status(500).build();
        });
  }

  public Mono<ServerResponse> modifyUser(ServerRequest request) {
    Mono<User> unsavedUser = request.bodyToMono(User.class);
    String id = request.pathVariable("id");

    Mono<User> updatedUser = unsavedUser.flatMap(u -> userService.modify(id, u));

    return updatedUser.flatMap(data -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(data))
        .onErrorResume(error -> {
          if(error instanceof ApiException) {
            return ServerResponse.status(((ApiException) error).getError().getStatus()).bodyValue(((ApiException) error).getError().getMessage());
          }
          return ServerResponse.status(500).build();
        });
  }

  public Mono<ServerResponse> deleteUser(ServerRequest request) {
    String id = request.pathVariable("id");
    Mono<Void> deleted = userService.delete(id);

    return deleted.flatMap(data -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).build())
        .onErrorResume(error -> {
          if(error instanceof ApiException) {
            return ServerResponse.status(((ApiException) error).getError().getStatus()).bodyValue(((ApiException) error).getError().getMessage());
          }
          return ServerResponse.status(500).build();
        });
  }
}

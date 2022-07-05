package com.example.backend_webflux;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class UserHandler {

  private final UserRepository userRepository;

  public UserHandler(UserRepository userRepository) {
    this.userRepository = userRepository;
  }


  public Mono<ServerResponse> getAllUser(ServerRequest request) {
    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(userRepository.findAll(), User.class);
  }

  public Mono<ServerResponse> getUser(ServerRequest request) {
    Integer id = Integer.valueOf(request.pathVariable("id"));

    return userRepository.findById(id)
        .flatMap(user -> ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(fromValue(user))
        ).switchIfEmpty(ServerResponse.notFound().build());
  }

  public Mono<ServerResponse> createUser(ServerRequest request) {
    Mono<User> unsavedUser = request.bodyToMono(User.class);

    return unsavedUser
        .flatMap(user -> userRepository.save(user)
            .flatMap(savedUser -> ServerResponse.accepted()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(savedUser))
            )
        ).switchIfEmpty(ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).build());
  }

  public Mono<ServerResponse> modifyUser(ServerRequest request) {
    Mono<User> unsavedUser = request.bodyToMono(User.class);
    Integer id = Integer.valueOf(request.pathVariable("id"));

    Mono<User> updatedUser = unsavedUser.flatMap(user ->
        userRepository.findById(id)
            .flatMap(oldUser -> {
              user.setId(id);
              return userRepository.save(user);
            }));

    return updatedUser
        .flatMap(user ->
        ServerResponse.accepted()
            .contentType(MediaType.APPLICATION_JSON)
            .body(fromValue(user))
        ).switchIfEmpty(ServerResponse.notFound().build());
  }

  public Mono<ServerResponse> deleteUser(ServerRequest request) {
    Integer id = Integer.valueOf(request.pathVariable("id"));
    Mono<Void> deleted = userRepository.deleteById(id);

    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(deleted, Void.class);
  }
}

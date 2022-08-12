package com.example.backend_webflux.handler;

import com.example.backend_webflux.dto.AuthDto;
import com.example.backend_webflux.exception.ApiException;
import com.example.backend_webflux.exception.ApiExceptionEnum;
import com.example.backend_webflux.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
public class AuthHandler {

  private final UserService userService;

  public Mono<ServerResponse> login(ServerRequest request) {
    Mono<AuthDto.Request> dto = request.bodyToMono(AuthDto.Request.class);

    Mono<String> token = dto.flatMap(userService::getUserByName);

    return token.flatMap(t -> ServerResponse.ok().header("token", t).build());
  }
}

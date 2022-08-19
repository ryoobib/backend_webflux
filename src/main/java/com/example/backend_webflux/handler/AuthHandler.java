package com.example.backend_webflux.handler;

import com.example.backend_webflux.domain.Token;
import com.example.backend_webflux.dto.AuthDto.Request;
import com.example.backend_webflux.service.SecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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

  private final SecurityService securityService;

  public Mono<ServerResponse> login(ServerRequest request) {
    Mono<Request> dto = request.bodyToMono(Request.class);

    Mono<Token> response =  dto
        .flatMap(d -> securityService.authenticate(d.getName(), d.getPassword())
        .map(token -> {
          Token t = new Token();
          BeanUtils.copyProperties(token, t);
          return t;
        }));
    return ServerResponse.ok().body(response, Token.class);
  }
}

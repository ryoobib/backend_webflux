package com.example.backend_webflux.sercurity;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenAuthenticationFilter implements WebFilter {
  public static final String HEADER_PREFIX = "Bearer ";

  private final JwtProvider jwtProvider;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
   String token = getToken(exchange.getRequest());
   if (StringUtils.hasText(token) && jwtProvider.validateToken(token)) {
     Authentication auth = new UsernamePasswordAuthenticationToken(token, token,
         List.of(new SimpleGrantedAuthority("ROLE_USER")));
     return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
   }
   return chain.filter(exchange);
  }

  private String getToken(ServerHttpRequest request) {
    String bearerToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(HEADER_PREFIX)) {
      return bearerToken.substring(7);
    }
    return null;
  }
}

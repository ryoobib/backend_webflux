package com.example.backend_webflux.sercurity;

import com.example.backend_webflux.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

  private final AuthenticationManager authenticationManager;
  private final SecurityContextRepository securityContextRepository;


  @Value("${app.public_routes}")
  private String[] publicRoutes;

  @Bean
  public ReactiveUserDetailsService userDetailsService(UserRepository userRepository) {
    return username -> userRepository.findByName(username)
        .map(user -> User.withDefaultPasswordEncoder()
            .username(user.getName())
            .password(user.getPassword())
            .authorities(user.getRoles().toArray(new String[0]))
            .build());
  }

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, AuthenticationManager authManager) {
    return http.cors().disable()
        .exceptionHandling()
        .authenticationEntryPoint((swe, e) -> Mono.fromRunnable(() -> {
          swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        })).accessDeniedHandler((swe, e) -> Mono.fromRunnable(() -> {
          swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        })).and()
        .csrf().disable()
        .authenticationManager(authenticationManager)
        .securityContextRepository(securityContextRepository)
        .authorizeExchange()
        .pathMatchers(publicRoutes).permitAll()
        .pathMatchers(HttpMethod.OPTIONS).permitAll()
        .anyExchange().authenticated()
        .and()
        .build();
  }

}

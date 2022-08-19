package com.example.backend_webflux.service;

import com.example.backend_webflux.domain.Token;
import com.example.backend_webflux.domain.User;
import com.example.backend_webflux.exception.ApiException;
import com.example.backend_webflux.exception.ApiExceptionEnum;
import com.example.backend_webflux.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class SecurityService implements Serializable {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Value("${spring.jwt.secret}")
  private String secret;

  @Value("${spring.jwt.expiration}")
  private String defaultExpirationTimeInSecondsConf;

  public Token generateAccessToken(User user) {
    var claims = new HashMap<String, Object>() {{
      put("role", user.getRoles());
    }};

    return doGenerateToken(claims, user.getName(), user.getId());
  }

  private Token doGenerateToken(Map<String, Object> claims, String issuer, String subject) {
    var expirationTimeInMilliseconds = Long.parseLong(defaultExpirationTimeInSecondsConf) * 1000;
    var expirationDate = new Date(new Date().getTime() + expirationTimeInMilliseconds);

    return doGenerateToken(expirationDate, claims, issuer, subject);
  }

  private Token doGenerateToken(Date expirationDate, Map<String, Object> claims, String issuer, String subject) {
    var createdDate = new Date();
    var token = Jwts.builder()
        .setClaims(claims)
        .setIssuer(issuer)
        .setSubject(subject)
        .setIssuedAt(createdDate)
        .setId(UUID.randomUUID().toString())
        .setExpiration(expirationDate)
        .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secret.getBytes()))
        .compact();

    return Token.builder()
        .token(token)
        .issuedAt(createdDate)
        .expiresAt(expirationDate)
        .build();
  }

  public Mono<Token> authenticate(String username, String password) {
    return userRepository.findByName(username)
        .flatMap(user -> {
          if (!user.isEnabled())
            return Mono.error(new ApiException(ApiExceptionEnum.DUPLICATION_VALUE_EXCEPTION));

          if (!passwordEncoder.encode(password).equals(user.getPassword()))
            return Mono.error(new ApiException(ApiExceptionEnum.BAD_REQUEST_EXCEPTION));

          return Mono.just(generateAccessToken(user).toBuilder()
              .userId(user.getId())
              .build());
        })
        .switchIfEmpty(Mono.error(new ApiException(ApiExceptionEnum.USER_NOT_FOUND_EXCEPTION)));
  }
}
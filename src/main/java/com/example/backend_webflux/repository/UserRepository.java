package com.example.backend_webflux.repository;

import com.example.backend_webflux.domain.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {

  Mono<User> findByName(String name);

  Boolean existsByName(String name);
}

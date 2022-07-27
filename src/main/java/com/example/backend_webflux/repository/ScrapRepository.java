package com.example.backend_webflux.repository;

import com.example.backend_webflux.domain.Post;
import com.example.backend_webflux.domain.Scrap;
import com.example.backend_webflux.domain.User;
import org.reactivestreams.Publisher;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ScrapRepository extends ReactiveMongoRepository<Scrap, String> {

  

  Mono<Scrap> findByPost(Post p);

  Flux<Scrap> findByUser(User u);
}

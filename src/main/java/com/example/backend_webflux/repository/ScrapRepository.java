package com.example.backend_webflux.repository;

import com.example.backend_webflux.domain.Scrap;
import org.reactivestreams.Publisher;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ScrapRepository extends ReactiveMongoRepository<Scrap, String> {

  Flux<Scrap> findByUserId(String userId);
}

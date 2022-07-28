package com.example.backend_webflux.repository;

import com.example.backend_webflux.domain.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PostRepository extends ReactiveMongoRepository<Post, String> {

  Flux<Post> findByTitleContains(String title, Pageable pageable);

  Flux<Post> findByUser(String user);

}

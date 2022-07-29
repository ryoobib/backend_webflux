package com.example.backend_webflux.repository;

import com.example.backend_webflux.domain.Comment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {

}

package com.pravanjan.spring_webflux_authentication.repository;

import com.pravanjan.spring_webflux_authentication.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Mono<User> findByUserName(String userName);
}

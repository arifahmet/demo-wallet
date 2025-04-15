package com.arif.demo.repository;

import com.arif.demo.model.entity.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<UserEntity, Long> {
    Mono<UserEntity> findByUsername(String username);
    Mono<UserEntity> findByUserKey(String userKey);
}

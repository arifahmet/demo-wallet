package com.arif.demo.service;

import com.arif.demo.model.entity.UserEntity;
import com.arif.demo.model.web.user.GetUserResponseDto;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<UserEntity> getUserByUserName(String userName);

    Mono<UserEntity> getUserByUserKey(String userKey);

    Mono<GetUserResponseDto> getUserInfo();

    Mono<UserEntity> getUser();

    Mono<UserEntity> save(UserEntity user);
}

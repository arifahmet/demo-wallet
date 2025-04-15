package com.arif.demo.service.impl;

import com.arif.demo.model.entity.UserEntity;
import com.arif.demo.model.web.user.GetUserResponseDto;
import com.arif.demo.repository.UserRepository;
import com.arif.demo.security.TokenUtil;
import com.arif.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TokenUtil tokenUtil;


    @Override
    public Mono<UserEntity> getUserByUserName(String userName) {
        return userRepository.findByUsername(userName);
    }

    @Override
    public Mono<UserEntity> getUserByUserKey(String userKey) {
        return userRepository.findByUserKey(userKey);
    }

    @Override
    public Mono<GetUserResponseDto> getUser() {
        return tokenUtil.getUserKey().flatMap(userRepository::findByUserKey).map(GetUserResponseDto::of);
    }

    @Override
    public Mono<UserEntity> save(UserEntity user) {
        return userRepository.save(user);
    }
}

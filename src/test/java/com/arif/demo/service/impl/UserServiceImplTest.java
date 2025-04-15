package com.arif.demo.service.impl;

import com.arif.demo.model.entity.UserEntity;
import com.arif.demo.model.web.user.GetUserResponseDto;
import com.arif.demo.repository.UserRepository;
import com.arif.demo.security.TokenUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TokenUtil tokenUtil;

    @Test
    void getUser_whenUserFound_returnsMappedResponse() {
        String userKey = "testUserKey";
        UserEntity userEntity = UserEntity.builder().id(1L).username("testUser").userKey(userKey).build();
        GetUserResponseDto expectedResponse = GetUserResponseDto.of(userEntity);

        when(tokenUtil.getUserKey()).thenReturn(Mono.just(userKey));
        when(userRepository.findByUserKey(anyString())).thenReturn(Mono.just(userEntity));

        Mono<GetUserResponseDto> result = userService.getUser();

        StepVerifier.create(result)
                .expectNext(expectedResponse)
                .verifyComplete();
    }

    @Test
    void getUser_whenUserNotFound_returnsEmpty() {
        String userKey = "nonExistentUserKey";

        when(tokenUtil.getUserKey()).thenReturn(Mono.just(userKey));
        when(userRepository.findByUserKey(anyString())).thenReturn(Mono.empty());

        Mono<GetUserResponseDto> result = userService.getUser();

        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void getUser_whenTokenFails_returnsError() {
        when(tokenUtil.getUserKey()).thenReturn(Mono.error(new RuntimeException("Token error")));

        Mono<GetUserResponseDto> result = userService.getUser();

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && throwable.getMessage().equals("Token error"))
                .verify();
    }
}
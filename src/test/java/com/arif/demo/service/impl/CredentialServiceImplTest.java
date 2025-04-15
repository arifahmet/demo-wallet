package com.arif.demo.service.impl;

import com.arif.demo.exception.model.UnauthorizedException;
import com.arif.demo.model.entity.UserEntity;
import com.arif.demo.model.web.credential.SignInRequestDto;
import com.arif.demo.model.web.credential.SignInResponseDto;
import com.arif.demo.security.TokenUtil;
import com.arif.demo.security.model.TokenTypeEnum;
import com.arif.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CredentialServiceImplTest {

    @InjectMocks
    private CredentialServiceImpl credentialService;

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenUtil tokenUtil;


    @Test
    void testSignIn_SuccessfulLogin() {
        String username = "testUser";
        String password = "password";
        String hashedPassword = "hashedPassword";
        String userKey = "userKey";
        String accessToken = "accessToken";

        SignInRequestDto requestDto = new SignInRequestDto(username, password);
        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .passwordHash(hashedPassword)
                .userKey(userKey)
                .build();

        when(userService.getUserByUserName(anyString())).thenReturn(Mono.just(userEntity));
        when(passwordEncoder.matches(password, hashedPassword)).thenReturn(true);
        when(tokenUtil.createToken(userKey, TokenTypeEnum.ACCESS)).thenReturn(accessToken);

        StepVerifier.create(credentialService.signIn(requestDto))
                .expectNext(new SignInResponseDto(accessToken))
                .verifyComplete();

        verify(userService, times(1)).getUserByUserName(username);
        verify(passwordEncoder, times(1)).matches(password, hashedPassword);
        verify(tokenUtil, times(1)).createToken(userKey, TokenTypeEnum.ACCESS);
    }

    @Test
    void testSignIn_InvalidPassword() {
        String username = "testUser";
        String password = "password";
        String hashedPassword = "hashedPassword";

        SignInRequestDto requestDto = new SignInRequestDto(username, password);
        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .passwordHash(hashedPassword)
                .build();

        when(userService.getUserByUserName(anyString())).thenReturn(Mono.just(userEntity));
        when(passwordEncoder.matches(password, hashedPassword)).thenReturn(false);

        StepVerifier.create(credentialService.signIn(requestDto))
                .expectErrorMatches(throwable -> throwable instanceof Exception &&
                        "Username and Password not match".equals(throwable.getMessage()))
                .verify();

        verify(userService, times(1)).getUserByUserName(username);
        verify(passwordEncoder, times(1)).matches(password, hashedPassword);
        verify(tokenUtil, times(0)).createToken(any(), any());
    }

    @Test
    void testSignIn_UserNotFound() {
        String username = "testUser";
        String password = "password";

        SignInRequestDto requestDto = new SignInRequestDto(username, password);

        when(userService.getUserByUserName(username)).thenReturn(Mono.empty());

        StepVerifier.create(credentialService.signIn(requestDto))
                .expectErrorMatches(throwable -> throwable instanceof UnauthorizedException &&
                        "Username and Password not match".equals(throwable.getMessage()))
                .verify();

        verify(userService, times(1)).getUserByUserName(username);
        verify(passwordEncoder, times(0)).matches(any(), any());
        verify(tokenUtil, times(0)).createToken(any(), any());
    }
}
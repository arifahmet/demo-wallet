package com.arif.demo.service.impl;

import com.arif.demo.exception.UnauthorizedException;
import com.arif.demo.model.entity.UserEntity;
import com.arif.demo.model.web.credential.SignInRequestDto;
import com.arif.demo.model.web.credential.SignInResponseDto;
import com.arif.demo.model.web.credential.SignUpRequestDto;
import com.arif.demo.model.web.credential.SignUpResponseDto;
import com.arif.demo.security.TokenUtil;
import com.arif.demo.security.model.TokenTypeEnum;
import com.arif.demo.service.CredentialService;
import com.arif.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CredentialServiceImpl implements CredentialService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final TokenUtil tokenUtil;

    @Override
    public Mono<SignInResponseDto> signIn(SignInRequestDto signInRequest) {
        return userService.getUserByUserName(signInRequest.username()).flatMap(user -> {
            if (!passwordEncoder.matches(signInRequest.password(), user.getPasswordHash())) {
                return Mono.error(new Exception("Username and Password not match"));
            }
            var accessToken = tokenUtil.createToken(user.getUserKey(), TokenTypeEnum.ACCESS);
            return Mono.just(new SignInResponseDto(accessToken));
        }).switchIfEmpty(Mono.error(new UnauthorizedException("Username and Password not match")));
    }

    @Override
    public Mono<SignUpResponseDto> signUp(SignUpRequestDto signUpRequest) {
        return saveUser(signUpRequest)
                .flatMap(userEntity -> Mono.just(SignUpResponseDto.of(userEntity)));
    }

    private Mono<UserEntity> saveUser(SignUpRequestDto request) {
        var encodedPwd = passwordEncoder.encode(request.password());
        return userService.save(UserEntity.of(request.username(), encodedPwd));
    }
}

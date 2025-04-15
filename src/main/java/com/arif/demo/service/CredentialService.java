package com.arif.demo.service;

import com.arif.demo.model.web.credential.SignInRequestDto;
import com.arif.demo.model.web.credential.SignInResponseDto;
import com.arif.demo.model.web.credential.SignUpRequestDto;
import com.arif.demo.model.web.credential.SignUpResponseDto;
import reactor.core.publisher.Mono;

public interface CredentialService {

    Mono<SignInResponseDto> signIn(SignInRequestDto signInRequest);

    Mono<SignUpResponseDto> signUp(SignUpRequestDto signUpRequest);
}

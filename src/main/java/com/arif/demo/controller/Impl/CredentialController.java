package com.arif.demo.controller.Impl;

import com.arif.demo.controller.CredentialOperation;
import com.arif.demo.model.web.credential.SignInRequestDto;
import com.arif.demo.model.web.credential.SignInResponseDto;
import com.arif.demo.model.web.credential.SignUpRequestDto;
import com.arif.demo.model.web.credential.SignUpResponseDto;
import com.arif.demo.service.CredentialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class CredentialController implements CredentialOperation {
    private final CredentialService credentialService;

    @Override
    public Mono<SignInResponseDto> signIn(SignInRequestDto signInRequest) {
        return credentialService.signIn(signInRequest);
    }

    @Override
    public Mono<SignUpResponseDto> signup(@RequestBody @Valid SignUpRequestDto signupRequest) {
        return credentialService.signUp(signupRequest);
    }
}

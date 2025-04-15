package com.arif.demo.controller.Impl;

import com.arif.demo.controller.UserOperation;
import com.arif.demo.model.web.user.GetUserResponseDto;
import com.arif.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class UserController implements UserOperation {

    private final UserService userService;

    @Override
    public Mono<GetUserResponseDto> getUser() {
        return userService.getUser();
    }
}


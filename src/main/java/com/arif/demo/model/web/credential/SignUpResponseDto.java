package com.arif.demo.model.web.credential;

import com.arif.demo.model.entity.UserEntity;
import lombok.Builder;

@Builder
public record SignUpResponseDto(String userName, String userKey) {

    public static SignUpResponseDto of(UserEntity userEntity) {
        return SignUpResponseDto.builder()
                .userName(userEntity.getUsername())
                .userKey(userEntity.getUserKey()).build();
    }
}

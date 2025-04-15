package com.arif.demo.model.web.user;

import com.arif.demo.model.entity.UserEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetUserResponseDto {
    private String username;
    private String userKey;

    public static GetUserResponseDto of(UserEntity userEntity) {
        return GetUserResponseDto.builder()
                .username(userEntity.getUsername())
                .userKey(userEntity.getUserKey())
                .build();
    }
}

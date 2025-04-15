package com.arif.demo.model.web.user;

import com.arif.demo.model.entity.UserEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetUserResponseDto {
    private String userKey;
    private String username;
    private String name;
    private String surname;
    private String tckn;


    public static GetUserResponseDto of(UserEntity userEntity) {
        return GetUserResponseDto.builder()
                .username(userEntity.getUsername())
                .userKey(userEntity.getUserKey())
                .name(userEntity.getName())
                .surname(userEntity.getSurname())
                .tckn(userEntity.getTckn())
                .build();
    }
}

package com.arif.demo.security.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class JwtToken {
    private String userKey;
    @JsonAlias({"loginTimeMilisecond", "loginTimeMillis"})
    private Long loginTimeMillisecond;
    @JsonAlias({"expirationTime", "expirationTimeMillis"})
    private Long expirationTime;
    private TokenTypeEnum tokenTypeEnum;
}

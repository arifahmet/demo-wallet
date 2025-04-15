package com.arif.demo.security;

import com.arif.demo.exception.model.TokenSerializationException;
import com.arif.demo.exception.model.UnauthorizedException;
import com.arif.demo.security.model.JwtToken;
import com.arif.demo.security.model.TokenTypeEnum;
import com.arif.demo.security.properties.JwtProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class TokenUtil {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public static final String TOKEN_CONTEXT_KEY = "JwtClaims";
    private static final String HMAC_SHA_512 = "HmacSHA512";
    private final JwtProperties jwtProperties;
    private final ObjectMapper objectMapper;


    public String createToken(String userKey, TokenTypeEnum tokenTypeEnum) {
        JwtToken jwtToken = buildJwtToken(userKey, tokenTypeEnum);
        Map<String, String> claims = new HashMap<>();

        try {
            claims.put("sub", OBJECT_MAPPER.writeValueAsString(jwtToken));
        } catch (JsonProcessingException e) {
            throw new TokenSerializationException("Failed to serialize JwtToken", e);
        }

        var key = parseSecretKey();
        return Jwts.builder().claims(claims).signWith(key).compact();
    }

    private Long getExpirationTime(TokenTypeEnum tokenTypeEnum) {
        return switch (tokenTypeEnum) {
            case ACCESS -> jwtProperties.getAccessExpirationTime();
            case REFRESH -> jwtProperties.getRefreshExpirationTime();
        };
    }

    private JwtToken buildJwtToken(String userKey, TokenTypeEnum tokenTypeEnum) {
        return JwtToken.builder()
                .userKey(userKey)
                .loginTimeMillisecond(System.currentTimeMillis())
                .expirationTime(System.currentTimeMillis() + getExpirationTime(tokenTypeEnum))
                .tokenTypeEnum(tokenTypeEnum)
                .build();
    }

    private SecretKey parseSecretKey() {
        return new SecretKeySpec(Base64.getDecoder().decode(jwtProperties.getSecretKey()), HMAC_SHA_512);
    }

    public JwtToken validateToken(String token) throws JsonProcessingException {
        var key = parseSecretKey();
        var subject = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
        var jwt = objectMapper.readValue(subject, JwtToken.class);
        if (isTokenExpired(jwt)) {
            throw new UnauthorizedException("Token expired");
        }
        return jwt;
    }

    private boolean isTokenExpired(JwtToken token) {
        return Objects.nonNull(token.getExpirationTime()) && token.getExpirationTime() < System.currentTimeMillis();
    }

    private Mono<JwtToken> getContext() {
        return Mono.deferContextual(contextView -> {
            try {
                JwtToken jwtToken = contextView.get(TOKEN_CONTEXT_KEY);
                return Mono.just(jwtToken);
            } catch (Exception e) {
                return Mono.error(e);
            }
        });
    }

    public Mono<String> getUserKey() {
        return getContext().map(JwtToken::getUserKey)
                .switchIfEmpty(Mono.error(new IllegalStateException("User key not found")));
    }
}

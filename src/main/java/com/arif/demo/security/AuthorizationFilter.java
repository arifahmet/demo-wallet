package com.arif.demo.security;


import com.arif.demo.security.model.JwtToken;
import com.arif.demo.security.properties.ExclusionUriProperty;
import com.arif.demo.security.properties.SecurityProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorizationFilter implements WebFilter {
    private final TokenUtil tokenUtil;
    private final SecurityProperties securityProperties;

    @Override
    public @NonNull Mono<Void> filter(@NonNull ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String authorizationHeader = getAuthorizationHeader(request);
        if (isSecurityBypass(request)) {
            return chain.filter(exchange);
        } else if (authorizationHeader != null) {
            authorizationHeader = authorizationHeader.replace("Bearer ", "");
            return validateTokenAndPropagateContext(authorizationHeader, exchange, chain);
        }
        return completeWithUnauthorizedResponse(response);
    }


    private boolean isSecurityBypass(ServerHttpRequest request) {
        if (Objects.isNull(securityProperties) || !securityProperties.isEnable()) {
            return true;
        }
        ExclusionUriProperty exclusionProperty = new ExclusionUriProperty(request.getPath().toString(), request.getMethod());
        return Objects.isNull(securityProperties.getExclusionUriPropertyList())
                || CollectionUtils.contains(securityProperties.getExclusionUriPropertyList().iterator(), exclusionProperty);
    }

    private String getAuthorizationHeader(ServerHttpRequest request) {
        return CollectionUtils.isEmpty(request.getHeaders().get(TokenUtil.AUTHORIZATION_HEADER))
                ? null
                : request.getHeaders().get(TokenUtil.AUTHORIZATION_HEADER).get(0);
    }

    private Mono<Void> validateTokenAndPropagateContext(String authorizationHeader, ServerWebExchange exchange, WebFilterChain chain) {
        try {
            JwtToken token = tokenUtil.validateToken(authorizationHeader);
            if (token != null) {
                return chain.filter(exchange).contextWrite(Context.of(TokenUtil.TOKEN_CONTEXT_KEY, token));
            }
        } catch (Exception e) {
            log.error("Token validation failed: ", e);
        }
        return completeWithUnauthorizedResponse(exchange.getResponse());
    }

    private Mono<Void> completeWithUnauthorizedResponse(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

}

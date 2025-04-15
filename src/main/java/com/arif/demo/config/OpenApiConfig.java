package com.arif.demo.config;

import com.arif.demo.security.TokenUtil;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Demo Application API",
                version = "1.0",
                description = "Demo Application For Case Study"
        ),
        security = @SecurityRequirement(name = TokenUtil.AUTHORIZATION_HEADER
        ))
@SecurityScheme(
        name = TokenUtil.AUTHORIZATION_HEADER,
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)

@Configuration
public class OpenApiConfig {
}

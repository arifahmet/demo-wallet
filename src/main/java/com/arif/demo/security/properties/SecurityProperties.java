package com.arif.demo.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
@ConfigurationProperties(prefix = "security")
@Data
public class SecurityProperties {
    private boolean enable;
    private Set<ExclusionUriProperty> exclusionUriPropertyList;
}

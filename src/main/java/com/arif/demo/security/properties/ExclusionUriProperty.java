package com.arif.demo.security.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ExclusionUriProperty {
    private String uri;
    private HttpMethod method;
}

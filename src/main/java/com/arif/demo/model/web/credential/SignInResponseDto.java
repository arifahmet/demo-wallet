package com.arif.demo.model.web.credential;

import lombok.Builder;

public record SignInResponseDto(String accessToken, String refreshToken) {
}

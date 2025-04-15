package com.arif.demo.exception.model;

import lombok.Builder;

import java.util.List;


@Builder
public record ErrorResponse(List<String> message) {
}

package com.web.service.application.dto;

import jakarta.validation.constraints.NotNull;

public record OrderProductDto(
        @NotNull
        Long productId,
        Integer quantity) {
}

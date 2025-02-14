package com.web.service.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRegisterDTO(
        @NotBlank
        String username,
        @NotBlank
        String email,
        @NotBlank
        String password,
        Integer role
) {
}

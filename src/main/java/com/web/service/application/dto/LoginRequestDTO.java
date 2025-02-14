package com.web.service.application.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        @NotBlank(message = "email deve ser preenchido")
        String email,
        @NotBlank(message = "senha deve ser preenchida!")
        String password
) {
}

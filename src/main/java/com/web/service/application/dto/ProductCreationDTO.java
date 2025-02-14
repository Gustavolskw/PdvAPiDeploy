package com.web.service.application.dto;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record ProductCreationDTO(String name, String description, BigDecimal value, Long typeId) {
}

package com.web.service.application.dto;

import java.math.BigDecimal;

public record ProductUpdateDTO(String name, String description, BigDecimal value, Long typeId, Boolean status) {
}

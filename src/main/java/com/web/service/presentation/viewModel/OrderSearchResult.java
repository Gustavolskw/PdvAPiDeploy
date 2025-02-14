package com.web.service.presentation.viewModel;

import java.time.LocalDateTime;

public record OrderSearchResult(
        Long id,
        String consumerName,
        Integer tableNumber,
        String guideName,
        Boolean status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public OrderSearchResult(Long id, String consumerName, Integer tableNumber, String guideName, Boolean status,
                             LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.consumerName = consumerName;
        this.tableNumber = tableNumber;
        this.guideName = guideName;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
package com.web.service.presentation.viewModel;

import com.web.service.domain.model.Order;
import com.web.service.domain.model.ProductOrder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderResponse(Long orderId,
                            Integer tableNumber,
                            String consumerName,
                            String guideName,
                            Boolean status,
                            BigDecimal totalValue,
                            Integer productsInOrder,
                            Integer itemsQuantity,
                            LocalDateTime createdAt,
                            LocalDateTime updatedAt) {
    public OrderResponse(Order o){
        this(o.getId(),
                o.getTableNumber(),
                o.getConsumerName(),
                o.getGuide().getUsername(),
                o.getStatus(),
                o.getProductsOnOrder().stream()
                        .map(
                        productOrder ->
                                productOrder.getProduct()
                                        .getValue()
                                        .multiply(BigDecimal.valueOf(productOrder.getQuantity()))
                ).reduce(BigDecimal.ZERO, BigDecimal::add),
                o.getProductsOnOrder().size(),
                o.getProductsOnOrder().stream().map(ProductOrder::getQuantity).reduce(Integer::sum).orElse(0),
                o.getCreatedAt(),
                o.getUpdatedAt()
        );
    }

}

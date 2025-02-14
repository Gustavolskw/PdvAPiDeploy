package com.web.service.presentation.viewModel;

import com.web.service.domain.model.Order;
import com.web.service.domain.model.ProductOrder;

import java.math.BigDecimal;

public record ProductOrderResponse(Long productId,
                                   String productName,
                                   String productDescription,
                                   String imageUrl,
                                   BigDecimal value,
                                   Integer quantity,
                                   BigDecimal total

) {
    public ProductOrderResponse(ProductOrder po){
        this(po.getProduct().getId(),
                po.getProduct().getName(),
                po.getProduct().getDescription(),
                po.getProduct().getImageUrl(),
                po.getProduct().getValue(),
                po.getQuantity(),
                po.getProduct().getValue().multiply(BigDecimal.valueOf(po.getQuantity()))
        );
    }

}

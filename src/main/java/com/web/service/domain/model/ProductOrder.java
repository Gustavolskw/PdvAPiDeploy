package com.web.service.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "PRODUCT_ORDER")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrder {
    @EmbeddedId
    private ProductOrderId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId") // Maps the orderId in ProductOrderId
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId") // Maps the productId in ProductOrderId
    @JoinColumn(name = "product_id")
    private Product product;
    private Integer quantity;
}
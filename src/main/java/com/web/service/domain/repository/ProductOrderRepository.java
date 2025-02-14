package com.web.service.domain.repository;

import com.web.service.domain.model.ProductOrder;
import com.web.service.domain.model.ProductOrderId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {
    Optional<ProductOrder> findByOrderIdAndProductId(Long orderId, Long productId);
    void deleteByOrderIdAndProductId(Long orderId, Long productId);

    void deleteById_OrderId(Long orderId);
}

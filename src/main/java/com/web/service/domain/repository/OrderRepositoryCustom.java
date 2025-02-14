package com.web.service.domain.repository;

import com.web.service.domain.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface OrderRepositoryCustom {
    Page<Order> findByParams(String consumerName, String guideName, Integer tableNumber, Pageable pageable);
}

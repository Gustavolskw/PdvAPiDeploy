package com.web.service.domain.validation;

import com.web.service.domain.model.Order;

public interface OrderValidation {
    void validateOrder(Long id);
    void validateTable(Integer tableNumber);
    void ValidateConsumerName(String consumerName);
    void validateGuide(Long guide);
    void validateProductOnOrder(Long productId, Long id);
    void validateUpdateProductOnOrder(Long productId, Long id);
    void validateQuantityProductOrder(Integer quantityProductOrder);
    void validateToExclude(Order id);
    void validateToClose(Order order);
}

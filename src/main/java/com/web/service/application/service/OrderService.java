package com.web.service.application.service;

import com.web.service.application.dto.OrderPlacingDTO;
import com.web.service.application.dto.OrderProductDto;
import com.web.service.domain.exception.EntityNotFoundException;
import com.web.service.domain.exception.ListEmptyException;
import com.web.service.domain.model.Order;
import com.web.service.domain.model.User;
import com.web.service.domain.repository.OrderRepository;
import com.web.service.domain.validation.OrderValidation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderValidation orderValidation;
    private final UserService userService;
    private final ProductOrderService productOrderService;


    public Long placeOrder(OrderPlacingDTO orderPlacingDTO) {
        orderValidation.validateTable(orderPlacingDTO.tableNum());
        orderValidation.ValidateConsumerName(orderPlacingDTO.consumerName());
        orderValidation.validateGuide(orderPlacingDTO.guide());
        User guide = userService.findById(orderPlacingDTO.guide());
        Order newOrder = buildOrder(orderPlacingDTO, guide);
        orderRepository.save(newOrder);
        return newOrder.getId();
    }

    public void includeProductOnOrder(Long id, OrderProductDto orderProductDto) {
        orderValidation.validateOrder(id);
        orderValidation.validateProductOnOrder(orderProductDto.productId(), id);
        orderValidation.validateUpdateProductOnOrder(orderProductDto.productId(), id);
        orderValidation.validateQuantityProductOrder(orderProductDto.quantity());
        if(productOrderService.getPorductOrderByPorductAndOrderOrNot(id, orderProductDto.productId()) == null) {
            productOrderService.includeProduct(findById(id), orderProductDto);
        }else {
            productOrderService.increaseQuantityOnOrder(id, orderProductDto.productId(), orderProductDto.quantity());
        }
    }

    public void decreaseProductOnOrder(Long id, OrderProductDto orderProductDto) {
        orderValidation.validateOrder(id);
        orderValidation.validateUpdateProductOnOrder(orderProductDto.productId(), id);
        orderValidation.validateProductOnOrder(orderProductDto.productId(), id);
        productOrderService.decrementProduct(id, orderProductDto.productId());
    }

    public void increaseProductOnOrder(Long id, OrderProductDto orderProductDto) {
        orderValidation.validateOrder(id);
        orderValidation.validateUpdateProductOnOrder(orderProductDto.productId(), id);
        orderValidation.validateProductOnOrder(orderProductDto.productId(), id);
        productOrderService.increaseQuantityOnOrder(id, orderProductDto.productId(), null);
    }

    public void adjustProductOnOrder(Long id, OrderProductDto orderProductDto) {
        orderValidation.validateOrder(id);
        orderValidation.validateUpdateProductOnOrder(orderProductDto.productId(), id);
        orderValidation.validateProductOnOrder(orderProductDto.productId(), id);
        orderValidation.validateQuantityProductOrder(orderProductDto.quantity());
        productOrderService.adjustQuantityOnOrder(id, orderProductDto.productId(), orderProductDto.quantity());
    }

    public void excludeProductOnOrder(Long id, Long productId) {
        orderValidation.validateOrder(id);
        orderValidation.validateProductOnOrder(productId, id);
        productOrderService.excludeProduct(id, productId);
    }

    public Order findById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if(order.isEmpty())throw new EntityNotFoundException("Order not found");
        return order.get();
    }

    @Transactional
    public void closeOrder(Long id) {
        Order order = findById(id);
        orderValidation.validateToClose(order);
        order.setStatus(false);
    }
    @Transactional
    public void cancelOrder(Long id) {
        Order order = findById(id);
        orderValidation.validateToExclude(order);
        productOrderService.orderHasBeenDeleted(id);
        orderRepository.delete(order);
    }

    public Order buildOrder(OrderPlacingDTO orderDto, User guide) {
        Order order = new Order();
        order.setConsumerName(orderDto.consumerName());
        order.setTableNumber(orderDto.tableNum());
        order.setGuide(guide);
        order.setStatus(true);
        return order;
    }

    public Page<Order> getOrdersByParam(String guide, String consumer, Integer table, Pageable pageable) {
        Page<Order> listOfOrders = orderRepository.findByParams(consumer, guide, table, pageable);
        if(listOfOrders.isEmpty()){
            throw new ListEmptyException("Lista de pedidos esta vazia");
        }
        return listOfOrders;
    }



}

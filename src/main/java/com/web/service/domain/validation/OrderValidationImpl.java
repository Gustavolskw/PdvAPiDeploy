package com.web.service.domain.validation;

import com.web.service.domain.exception.EntityNotFoundException;
import com.web.service.domain.exception.OrderProcessError;
import com.web.service.domain.exception.UnprocessableAction;
import com.web.service.domain.exception.ValidationException;
import com.web.service.domain.model.Order;
import com.web.service.domain.repository.OrderRepository;
import com.web.service.domain.repository.ProductRepository;
import com.web.service.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderValidationImpl implements OrderValidation {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;


    @Override
    public void validateOrder(Long id) {
        if(!orderRepository.existsById(id))throw new OrderProcessError("Ordem apontada inexistente!");
    }

    @Override
    public void validateTable(Integer tableNumber) {
        if(tableNumber==null)throw new ValidationException("Número da mesa deve ser preenchido");
        if(tableNumber==0) throw new ValidationException("Número da mesa deve ser diferente de 0");
        Integer tableCountUsed  = orderRepository.countTableAmountUsed(tableNumber);
        if(tableCountUsed>5)throw new OrderProcessError("Mesa ultilizada mais de 5 vezes!");
    }

    @Override
    public void ValidateConsumerName(String consumerName) {
        if(consumerName.isEmpty())throw new ValidationException("Consumer name deve ser preenchido");
    }

    @Override
    public void validateGuide(Long guide) {
        if(guide==null) throw new ValidationException("Guide deve ser preenchido");
        if(!userRepository.existsById(guide)) throw new EntityNotFoundException("Guia não encontrado!");
    }

    @Override
    public void validateProductOnOrder(Long productId, Long orderId) {
        if(productId==null) throw new ValidationException("Product id deve ser preenchido");
        if(!productRepository.existsById(productId))throw new EntityNotFoundException("Produto inexistente!");
        if(!productRepository.isValid(productId))throw new OrderProcessError("Produto Inativo!");
    }

    @Override
    public void validateUpdateProductOnOrder(Long productId, Long orderId) {
        Order orderRequested = orderRepository.findById(orderId).orElseThrow(()->new EntityNotFoundException("Order inexistente!"));
        if(!orderRequested.getStatus())throw new OrderProcessError("Ordem apontada está inativada!");
    }

    @Override
    public void validateQuantityProductOrder(Integer quantityProductOrder) {
        if(quantityProductOrder == null) throw new ValidationException("Quantidade deve ser preenchido");
        if(quantityProductOrder<=0) throw new OrderProcessError("Quantidade à consumir deve ser maior que 0");
    }

    @Override
    public void validateToExclude(Order order) {
        if(!order.getStatus()) throw new UnprocessableAction("Ordem ja se encontra fechada, não pode ser excluida!");
    }

    @Override
    public void validateToClose(Order order) {
        if(order.getProductsOnOrder().isEmpty()) throw new OrderProcessError("Ordem apontada para fechamento não possui itens!");
    }
}

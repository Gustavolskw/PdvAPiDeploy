package com.web.service.application.service;

import com.web.service.application.dto.OrderProductDto;
import com.web.service.domain.exception.EntityNotFoundException;
import com.web.service.domain.model.Order;
import com.web.service.domain.model.Product;
import com.web.service.domain.model.ProductOrder;
import com.web.service.domain.model.ProductOrderId;
import com.web.service.domain.repository.ProductOrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductOrderService {

    private final ProductService productService;
    private final ProductOrderRepository productOrderRepository;

    public void includeProduct(Order order, OrderProductDto orderProductDto) {
        ProductOrder newProductOrder = buildProductOrder(order,
                productService.findProductById(orderProductDto.productId()),
                orderProductDto.quantity());
        productOrderRepository.save(newProductOrder);
    }

    @Transactional
    public void decrementProduct(Long orderId, Long productId) {
        ProductOrder productOrderSel = getPorductOrderByPorductAndOrder(orderId, productId);
        productOrderSel.setQuantity(productOrderSel.getQuantity() - 1);
    }

    @Transactional
    public void adjustQuantityOnOrder(Long orderId, Long productId, Integer quantity) {
        ProductOrder productOrderSel = getPorductOrderByPorductAndOrder(orderId, productId);
        productOrderSel.setQuantity(quantity);
    }

    @Transactional
    public void increaseQuantityOnOrder(Long orderId, Long productId, Integer quantity) {
        ProductOrder productOrderSel = getPorductOrderByPorductAndOrder(orderId, productId);
        System.out.println("Quantidade atual do produto: "+ productOrderSel.getQuantity());
        System.out.println("Quantidade a inserir :"+quantity);
        if(quantity != null){
            productOrderSel.setQuantity(productOrderSel.getQuantity() + quantity);
        }else {
            productOrderSel.setQuantity(productOrderSel.getQuantity() + 1);
        }
    }

    @Transactional
    public void excludeProduct(Long orderId,Long productId) {
      productOrderRepository.deleteByOrderIdAndProductId(orderId,productId);
    }

    @Transactional
    public void orderHasBeenDeleted(Long orderId){
        productOrderRepository.deleteById_OrderId(orderId);
    }

    private ProductOrder getPorductOrderByPorductAndOrder(Long orderId, Long productId){
        Optional<ProductOrder> prodductorder = productOrderRepository.findByOrderIdAndProductId(orderId, productId);
        if (prodductorder.isEmpty())throw new EntityNotFoundException("ordem de produto não encontrada");
        return prodductorder.get();
    }

    public ProductOrder getPorductOrderByPorductAndOrderOrNot(Long orderId, Long productId){
        Optional<ProductOrder> prodductorder = productOrderRepository.findByOrderIdAndProductId(orderId, productId);
        return prodductorder.orElse(null);
    }

    private ProductOrder buildProductOrder(Order order, Product product, Integer quantity) {
        ProductOrder productOrder = new ProductOrder();
        ProductOrderId forKeyOrderProd = new ProductOrderId(order.getId(), product.getId());
        productOrder.setId(forKeyOrderProd);
        productOrder.setOrder(order);
        productOrder.setProduct(product);
        productOrder.setQuantity(quantity);
        return productOrder;
    }
}

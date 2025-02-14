package com.web.service.presentation.viewModel;

import com.web.service.domain.model.ProductType;

import java.util.Set;
import java.util.stream.Collectors;

public record ProductTypeResponseComplete(Long id, String name, Set<ProductResponseDTO> productsWithType) {
    public ProductTypeResponseComplete(ProductType productType){
        this(productType.getId(),
                productType.getName(),
                productType.getProductHasType().stream().map(ProductResponseDTO::new).collect(Collectors.toSet()));
    }
}

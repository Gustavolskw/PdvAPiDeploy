package com.web.service.presentation.viewModel;

import com.web.service.domain.model.ProductType;

import java.util.Set;
import java.util.stream.Collectors;

public record ProductTypeResponse(Long id, String name) {
    public ProductTypeResponse(ProductType productType){
        this(productType.getId(),
                productType.getName());
    }
}

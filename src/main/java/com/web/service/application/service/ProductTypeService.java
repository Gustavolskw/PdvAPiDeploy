package com.web.service.application.service;

import com.web.service.application.dto.ProductTypeCreation;
import com.web.service.domain.exception.ListEmptyException;
import com.web.service.domain.model.ProductType;
import com.web.service.domain.repository.ProductTypeRepository;
import com.web.service.domain.validation.ProductTypeValidation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductTypeService {

    private final ProductTypeRepository productTypeRepository;
    private final ProductTypeValidation productTypeValidation;


    @Transactional
    public ProductType createProductType(ProductTypeCreation productTypeCreation) {
        ProductType productType = new ProductType();
        productType.setName(productTypeCreation.name());
        productTypeRepository.save(productType);
        return productType;
    }


    public List<ProductType> getAllProductTypes() {
        List<ProductType> productTypes = productTypeRepository.findAll();
        if(productTypes.isEmpty())throw new ListEmptyException("Lista de Tipos de Produto vazia!");
        return productTypes;
    }

    public ProductType getProductTypeById(Long id) {
        Optional<ProductType> productType = productTypeRepository.findById(id);
        if(productType.isPresent())return productType.get();
        throw new ListEmptyException("Lista de Tipos de Produto vazia!");
    }

    public List<ProductType> getProductTypeByName(String name) {
        Optional<List<ProductType>> searchedByNameList = productTypeRepository.searchProdcutTypeByItsName(name+"%");
        if(searchedByNameList.isPresent())return searchedByNameList.get();
        throw new ListEmptyException("Lista de Tipos de Produto vazia!");
    }

    @Transactional
    public void deleteProductTypeById(Long id) {
        productTypeValidation.validateToDeleteProductType(id);
        productTypeRepository.deleteById(id);
    }
}

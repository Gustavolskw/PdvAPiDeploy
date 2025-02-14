package com.web.service.domain.validation;

import com.web.service.domain.exception.AlreadyExistsException;
import com.web.service.domain.exception.UnprocessableAction;
import com.web.service.domain.exception.ValidationException;
import com.web.service.domain.repository.ProductRepository;
import com.web.service.domain.repository.ProductTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductTypeValidationImpl  implements ProductTypeValidation {

    private final ProductTypeRepository productTypeRepository;
    private final ProductRepository productRepository;
    @Override
    public void validateName(String productTypeName) {
        if (productTypeName.isEmpty()) throw new ValidationException("Product type name cannot be empty");
    }

    @Override
    public void validateNewProductType(String productTypeName) {
        validateName(productTypeName);
        if(productTypeRepository.findByName(productTypeName)) throw new AlreadyExistsException("Product type already exists");
    }

    @Override
    public void validateToDeleteProductType(Long id) {
        if(id == null )throw new ValidationException("Product type id cannot be null");
        if(productTypeRepository.findById(id).isEmpty()) throw new AlreadyExistsException("Product type doesn't exists");
        if(productRepository.existsByProductTypeId(id))throw new UnprocessableAction("There is a product that uses this type id!");
    }
}

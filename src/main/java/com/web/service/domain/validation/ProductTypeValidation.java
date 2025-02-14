package com.web.service.domain.validation;

public interface ProductTypeValidation {

    void validateName(String productTypeName);
    void validateNewProductType(String productTypeName);
    void validateToDeleteProductType(Long id);
}

package com.web.service.domain.validation;

import java.math.BigDecimal;

public interface ProductValidation {

     void validateName(String name);
     void validateNameInsert(String name);
     void validatePrice(BigDecimal price);
     void validateDescription(String description);
     void validateTypeExistence(Long typeId);

}

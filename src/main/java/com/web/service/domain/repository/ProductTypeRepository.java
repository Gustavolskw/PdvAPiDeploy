package com.web.service.domain.repository;

import com.web.service.domain.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
    boolean findByName(String name);


    @Query(nativeQuery = true, value = """
SELECT * FROM CARDAPIO_LOJA.product_type p WHERE p.name LIKE :typeName; 
""")
    Optional<List<ProductType>> searchProdcutTypeByItsName(@Param(value = "typeName") String typeName);


}

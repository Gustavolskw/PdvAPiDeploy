package com.web.service.domain.repository;

import com.web.service.domain.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String productName);

    @Query(nativeQuery = true, value = "SELECT status from products p where id=:productId")
    Integer isValid(@Param(value = "productId") Long productId);

    @Query(nativeQuery = true, value = """
        SELECT * FROM CARDAPIO_LOJA.products p
        WHERE
        (:status IS NULL OR p.status = :status)
        AND (:name IS NULL OR p.name LIKE :name)
        AND (:type IS NULL OR p.type_id = :type)
    """,
            countQuery = """
        SELECT COUNT(*) FROM CARDAPIO_LOJA.products p
        WHERE
        (:status IS NULL OR p.status = :status)
        AND (:name IS NULL OR p.name LIKE :name)
        AND (:type IS NULL OR p.type_id = :type)
    """)
    Page<Product> searchProductsByParam(@Param("status") Boolean status, @Param("name") String name, @Param("type") Long type, Pageable pageable);

    boolean existsByProductTypeId(Long id);
}

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
    boolean isValid(@Param(value = "productId") Long productId);

    @Query(nativeQuery = true, value = """
    SELECT * FROM products p
    WHERE
    (:status IS NULL OR p.status = CAST(:status AS BOOLEAN))
    AND (:name IS NULL OR p.name LIKE CONCAT('%', :name, '%'))
    AND (:type IS NULL OR p.type_id = CAST(:type AS BIGINT))
    """,
            countQuery = """
    SELECT COUNT(*) FROM products p
    WHERE
    (:status IS NULL OR p.status = CAST(:status AS BOOLEAN))
    AND (:name IS NULL OR p.name LIKE CONCAT('%', :name, '%'))
    AND (:type IS NULL OR p.type_id = CAST(:type AS BIGINT))
    """)
    Page<Product> searchProductsByParam(@Param("status") Boolean status, @Param("name") String name, @Param("type") Long type, Pageable pageable);

    boolean existsByProductTypeId(Long id);
}

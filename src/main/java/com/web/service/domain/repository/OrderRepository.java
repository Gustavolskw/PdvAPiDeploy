package com.web.service.domain.repository;

import com.web.service.domain.model.Order;
import com.web.service.domain.model.Product;
import com.web.service.presentation.viewModel.OrderSearchResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {


    @Query(nativeQuery = true, value = "SELECT COUNT(id) AS quantidade FROM orders WHERE table_number = :tableNumber")
    public Integer countTableAmountUsed(@Param(value = "tableNumber") Integer tableNumber);


    @Query(nativeQuery = true, value = """
            SELECT o.* FROM orders o
            INNER JOIN users u ON o.guide = u.id
            WHERE (:consumerName IS NULL OR o.consumer_name LIKE CONCAT('%', :consumerName, '%'))
            AND (:guideName IS NULL OR u.username LIKE CONCAT('%', :guideName, '%'))
            AND (:tableNumber IS NULL OR o.table_number = :tableNumber)
    """,
            countQuery = """
                    SELECT COUNT(o.id) FROM orders o
                            INNER JOIN users u ON o.guide = u.id
                            WHERE (:consumerName IS NULL OR o.consumer_name LIKE CONCAT('%', :consumerName, '%'))
                            AND (:guideName IS NULL OR u.username LIKE CONCAT('%', :guideName, '%'))
                            AND (:tableNumber IS NULL OR o.table_number = :tableNumber)
    """)
    Page<Order> findByParams(@Param("consumerName") String consumerName, @Param("guideName") String guideName, @Param("tableNumber") Integer tableNumber, Pageable pageable);
}

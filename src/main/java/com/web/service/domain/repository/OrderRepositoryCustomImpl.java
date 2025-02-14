package com.web.service.domain.repository;

import com.web.service.domain.model.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Order> findByParams(String consumerName, String guideName, Integer tableNumber, Pageable pageable) {
        String baseSql = "SELECT o.* FROM CARDAPIO_LOJA.orders o " +
                "INNER JOIN CARDAPIO_LOJA.users u ON o.guide = u.id " +
                "WHERE (:consumerName IS NULL OR o.consumer_name LIKE CONCAT('%', :consumerName, '%')) " +
                "AND (:guideName IS NULL OR u.username LIKE CONCAT('%', :guideName, '%')) " +
                "AND (:tableNumber IS NULL OR o.table_number = :tableNumber)";

        // Count query for total records
        String countSql = "SELECT COUNT(o.id) FROM CARDAPIO_LOJA.orders o " +
                "INNER JOIN CARDAPIO_LOJA.users u ON o.guide = u.id " +
                "WHERE (:consumerName IS NULL OR o.consumer_name LIKE CONCAT('%', :consumerName, '%')) " +
                "AND (:guideName IS NULL OR u.username LIKE CONCAT('%', :guideName, '%')) " +
                "AND (:tableNumber IS NULL OR o.table_number = :tableNumber)";

        // Create the count query
        Query countQuery = entityManager.createNativeQuery(countSql);
        countQuery.setParameter("consumerName", consumerName);
        countQuery.setParameter("guideName", guideName);
        countQuery.setParameter("tableNumber", tableNumber);

        // Get total count
        long total = ((Number) countQuery.getSingleResult()).longValue();

        // Create the main query
        Query query = entityManager.createNativeQuery(baseSql, Order.class);
        query.setParameter("consumerName", consumerName);
        query.setParameter("guideName", guideName);
        query.setParameter("tableNumber", tableNumber);

        // Apply pagination
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        // Get results
        @SuppressWarnings("unchecked")
        List<Order> orders = query.getResultList();

        // Return a Page object
        return new PageImpl<>(orders, pageable, total);
    }


}

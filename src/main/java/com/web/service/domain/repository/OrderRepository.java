package com.web.service.domain.repository;

import com.web.service.domain.model.Order;
import com.web.service.presentation.viewModel.OrderSearchResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {


    @Query(nativeQuery = true, value = "SELECT COUNT(id) AS quantidade FROM CARDAPIO_LOJA.orders WHERE table_number = :tableNumber")
    public Integer countTableAmountUsed(@Param(value = "tableNumber") Integer tableNumber);


//    @Query("SELECT new com.web.service.presentation.viewModel.OrderSearchResult(" +
//            "o.id, o.consumerName, o.tableNumber, u.username, o.status, o.createdAt, o.updatedAt) " +
//            "FROM Order o " +
//            "JOIN o.guide u " +
//            "WHERE (:consumerName IS NULL OR o.consumerName LIKE CONCAT('%', :consumerName, '%')) " +
//            "AND (:guideName IS NULL OR u.username LIKE CONCAT('%', :guideName, '%')) " +
//            "AND (:tableNumber IS NULL OR o.tableNumber = :tableNumber)")
//    List<OrderSearchResult> findByParamRequestOnController(@Param("consumerName") String consumerName,
//                                                           @Param("guideName") String guideName,
//                                                           @Param("tableNumber") Integer tableNumber);



}

package com.tmdtud.cuahang.api.order.repository;

import java.time.LocalDate;

//khác với hibernate Page
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tmdtud.cuahang.api.order.model.Orders;
import com.tmdtud.cuahang.api.order.model.OrderStatus;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {

    @Query("""
            SELECT o from Orders o WHERE o.deleted = 0
            AND (:fromDate is null OR o.createdAt >= :fromDate)
            AND (:toDate is null OR o.createdAt <= :toDate)
            AND (:status is null OR o.status = :status)
            """)
    Page<Orders> findAllByDateRange(
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate,
            @Param("status") OrderStatus status,
            Pageable pageable);

    @Query("SELECT o FROM Orders o WHERE o.customer.id = :customerId AND o.deleted = 0")
    Page<Orders> findByCustomerId(@Param("customerId") Long customerId, Pageable pageable);

    @Query("""
            SELECT COUNT(o) FROM Orders o WHERE o.deleted = 0
            AND (:fromDate is null OR o.createdAt >= :fromDate)
            AND (:toDate is null OR o.createdAt <= :toDate)
            AND (:status is null OR o.status = :status)
            """)
    long countOrders(
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate,
            @Param("status") OrderStatus status);

    @Query("""
            SELECT COALESCE(SUM(o.totalPrice), 0) FROM Orders o WHERE o.deleted = 0
            AND (:fromDate is null OR o.createdAt >= :fromDate)
            AND (:toDate is null OR o.createdAt <= :toDate)
            AND (:status is null OR o.status = :status)
            """)
    java.math.BigDecimal sumRevenue(
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate,
            @Param("status") OrderStatus status);
}

package com.tmdtud.cuahang.api.order_detail.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tmdtud.cuahang.api.order_detail.model.OrdersDetails;
import com.tmdtud.cuahang.api.order_detail.model.OrdersDetailsId;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrdersDetails, OrdersDetailsId> {
    @Modifying
    @Query(value = "DELETE FROM orders_details WHERE order_id = ?1", nativeQuery = true)
    int deleteByOrder(Long id);

    @Modifying
    @Query(value = "DELETE FROM orders_details WHERE order_id = ?1 and product_id = ?2", nativeQuery = true)
    int deleteByBothId(Long orderId, Long productId);

    @Query("SELECT o FROM OrdersDetails o WHERE o.id.orderId = :orderId AND o.id.productId = :productId")
    OrdersDetails getByBothId(@Param("orderId") Long orderId, @Param("productId") Long productId);

    @Query("SELECT o FROM OrdersDetails o WHERE o.id.orderId = :orderId")
    List<OrdersDetails> getByOrderId(@Param("orderId") Long orderId);
}

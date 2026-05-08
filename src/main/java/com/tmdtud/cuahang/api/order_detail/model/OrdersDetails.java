package com.tmdtud.cuahang.api.order_detail.model;


import java.math.BigDecimal;
import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import com.tmdtud.cuahang.api.order.model.Orders;
import com.tmdtud.cuahang.api.product.model.Products;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "orders_details")
@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class OrdersDetails{
    @EmbeddedId
    private OrdersDetailsId id;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id", updatable = false)
    private Orders order;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", updatable = false)
    private Products product;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private BigDecimal cost;

    @Column(nullable = false)
    private BigDecimal total;

    @Column
    private String color;

    @Column
    private String size;
}

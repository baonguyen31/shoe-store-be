package com.tmdtud.cuahang.api.order.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.tmdtud.cuahang.api.customer.model.Customers;
import com.tmdtud.cuahang.api.employer.model.Employers;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "orders")
@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Orders {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String method;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column
    private BigDecimal totalPrice;

    @Column
    private String paymentStatus;

    @Column
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column
    private int deleted;

    @ManyToOne
    @JoinColumn(name = "employer_id", nullable = true, foreignKey = @ForeignKey(name = "fk_PurchaseOrders_Employers"))
    private Employers employer;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = true, foreignKey = @ForeignKey(name = "fk_Orders_Suppliers"))
    private Customers customer;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "street")
    private String street;

    @Column(name = "ward")
    private String ward;

    @Column(name = "city")
    private String city;
}

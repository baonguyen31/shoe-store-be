package com.tmdtud.cuahang.api.product.model;

import java.math.BigDecimal;

import com.tmdtud.cuahang.api.brand.model.Brands;
import com.tmdtud.cuahang.api.category.model.Categories;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Table(name = "products")
@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Products {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column
    private BigDecimal price;

    @Column
    private int quantity;

    @Column
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "discount_percentage")
    private BigDecimal discountPercentage;

    @Column
    private BigDecimal rating;

    @Column
    private int deleted;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = true, foreignKey = @ForeignKey(name = "fk_products_categories"))
    private Categories category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "brand_id", nullable = true, foreignKey = @ForeignKey(name = "fk_products_brands"))
    private Brands brand;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private java.util.List<ProductVariant> variants;
}

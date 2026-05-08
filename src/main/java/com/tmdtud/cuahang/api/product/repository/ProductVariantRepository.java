package com.tmdtud.cuahang.api.product.repository;

import com.tmdtud.cuahang.api.product.model.ProductVariant;
import com.tmdtud.cuahang.api.product.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
    List<ProductVariant> findByProduct(Products product);
    Optional<ProductVariant> findByProductAndColorAndSize(Products product, String color, String size);
}

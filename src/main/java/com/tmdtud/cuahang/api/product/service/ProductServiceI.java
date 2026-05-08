package com.tmdtud.cuahang.api.product.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.tmdtud.cuahang.api.product.model.Products;
import com.tmdtud.cuahang.api.product.request.ProductStoreRequest;
import com.tmdtud.cuahang.api.product.request.ProductUpdateRequest;
import com.tmdtud.cuahang.common.response.PageResponse;

import java.math.BigDecimal;

public interface ProductServiceI {
    public PageResponse<Products> getAll(String name, Long categoryId, Long brandId, BigDecimal minPrice, BigDecimal maxPrice, String color, Pageable pageable);
    public PageResponse<Products> getAll(Pageable pageable);
    public PageResponse<Products> getBestSellers(String name, Long categoryId, Long brandId, BigDecimal minPrice, BigDecimal maxPrice, String color, Pageable pageable);

    public Products getById(Long id);

    public boolean delete(Long id);

    public Products add(ProductStoreRequest request);

    public Products update(ProductUpdateRequest request);

    public int setCategoryAndBrandDefaultByCategory(Long categoryId);

    public int setDefaultBrand(Long brandId);

    public List<Products> updateAll(List<Products> products);
}

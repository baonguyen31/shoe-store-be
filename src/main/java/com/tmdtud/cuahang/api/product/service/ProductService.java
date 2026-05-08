package com.tmdtud.cuahang.api.product.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tmdtud.cuahang.api.brand.model.Brands;
import com.tmdtud.cuahang.api.brand.service.BrandService;
import com.tmdtud.cuahang.api.category.model.Categories;
import com.tmdtud.cuahang.api.category.service.CategoryService;
import com.tmdtud.cuahang.api.product.model.Products;
import com.tmdtud.cuahang.api.product.repository.ProductRepository;
import com.tmdtud.cuahang.api.product.request.ProductStoreRequest;
import com.tmdtud.cuahang.api.product.request.ProductUpdateRequest;
import com.tmdtud.cuahang.common.response.PageResponse;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Service
@Data
@RequiredArgsConstructor
public class ProductService implements ProductServiceI {

    private final ProductRepository productRepo;
    private final com.tmdtud.cuahang.api.product.repository.ProductVariantRepository variantRepo;

    private final BrandService brandService;
    private final CategoryService categoryService;

    @Override
    public PageResponse<Products> getAll(String name, Long categoryId, Long brandId, BigDecimal minPrice, BigDecimal maxPrice, String color, Pageable pageable) {
        org.springframework.data.domain.Page<Products> products = productRepo.findProductsWithFilters(
            name != null && !name.isEmpty() ? name : null, 
            categoryId, 
            brandId, 
            minPrice, 
            maxPrice, 
            color,
            pageable
        );
        return new PageResponse<Products>(products);
    }

    @Override
    public PageResponse<Products> getAll(Pageable pageable) {
        org.springframework.data.domain.Page<Products> products = productRepo.findAll(pageable);
        return new PageResponse<Products>(products);
    }

    @Override
    public PageResponse<Products> getBestSellers(String name, Long categoryId, Long brandId, BigDecimal minPrice, BigDecimal maxPrice, String color, Pageable pageable) {
        org.springframework.data.domain.Page<Products> products = productRepo.findBestSellersWithFilters(
            name != null && !name.isEmpty() ? name : null, 
            categoryId, 
            brandId, 
            minPrice, 
            maxPrice, 
            color,
            pageable
        );
        return new PageResponse<Products>(products);
    }

    @Override
    public Products add(ProductStoreRequest request) {
        Brands brand = brandService.getById(request.getBrand_id());
        Categories category = categoryService.getById(request.getCategory_id());

        int totalQuantity = request.getQuantity();
        if (request.getVariants() != null && !request.getVariants().isEmpty()) {
            totalQuantity = request.getVariants().stream().mapToInt(v -> v.getQuantity()).sum();
        }

        Products product = Products.builder()
                .name(request.getName())
                .description(request.getDescription())
                .quantity(totalQuantity)
                .price(request.getPrice())
                .brand(brand)
                .imageUrl(request.getImageUrl())
                .discountPercentage(request.getDiscountPercentage())
                .rating(request.getRating())
                .category(category).build();

        Products savedProduct = productRepo.save(product);

        if (request.getVariants() != null) {
            List<com.tmdtud.cuahang.api.product.model.ProductVariant> variants = request.getVariants().stream().map(v -> 
                com.tmdtud.cuahang.api.product.model.ProductVariant.builder()
                    .product(savedProduct)
                    .color(v.getColor())
                    .size(v.getSize())
                    .quantity(v.getQuantity())
                    .build()
            ).collect(Collectors.toList());
            variantRepo.saveAll(variants);
        }

        return savedProduct;
    }

    @Override
    public boolean delete(Long id) {
        Products products = getById(id);
        if (products != null) {
            products.setDeleted(1);
            productRepo.save(products);
            return true;
        }
        return false;
    }

    @Override
    public Products getById(Long id) {
        Products product = productRepo.findById(id).orElse(null);
        return (product);
    }

    @Override
    public Products update(ProductUpdateRequest request) {
        Brands brand = brandService.getById(request.getBrand_id());
        Categories category = categoryService.getById(request.getCategory_id());

        int totalQuantity = request.getQuantity();
        if (request.getVariants() != null && !request.getVariants().isEmpty()) {
            totalQuantity = request.getVariants().stream().mapToInt(v -> v.getQuantity()).sum();
        }

        Products product = Products.builder()
                .id(request.getId())
                .name(request.getName())
                .description(request.getDescription())
                .quantity(totalQuantity)
                .price(request.getPrice())
                .brand(brand)
                .imageUrl(request.getImageUrl())
                .deleted(request.getDeleted())
                .discountPercentage(request.getDiscountPercentage())
                .rating(request.getRating())
                .category(category).build();
        
        Products savedProduct = productRepo.save(product);

        // Update variants
        if (request.getVariants() != null) {
            List<com.tmdtud.cuahang.api.product.model.ProductVariant> oldVariants = variantRepo.findByProduct(savedProduct);
            variantRepo.deleteAll(oldVariants);

            List<com.tmdtud.cuahang.api.product.model.ProductVariant> variants = request.getVariants().stream().map(v -> 
                com.tmdtud.cuahang.api.product.model.ProductVariant.builder()
                    .product(savedProduct)
                    .color(v.getColor())
                    .size(v.getSize())
                    .quantity(v.getQuantity())
                    .build()
            ).collect(Collectors.toList());
            variantRepo.saveAll(variants);
        }

        return savedProduct;
    }

    @Override
    public int setCategoryAndBrandDefaultByCategory(Long catogryId) {
        return productRepo.setDefaultCategoryAndBrandByCategory(catogryId);
    }

    @Override
    public int setDefaultBrand(Long brandId) {
        return productRepo.setDefaultBrand(brandId);
    }

    @Override
    public List<Products> updateAll(List<Products> products) {
        return productRepo.saveAll(products);
    }

}

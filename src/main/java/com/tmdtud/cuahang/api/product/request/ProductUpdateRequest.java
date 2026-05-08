package com.tmdtud.cuahang.api.product.request;

import java.math.BigDecimal;

import com.tmdtud.cuahang.api.brand.model.Brands;
import com.tmdtud.cuahang.api.category.model.Categories;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.tmdtud.cuahang.api.product.dto.ProductVariantDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateRequest {
    @NotNull(message = "id not null")
    private Long id;

    @NotBlank(message = "name not empty")
    @NotNull(message = "name not null")
    private String name;

    private String description;

    @NotNull(message = "price not null")
    @DecimalMin(value = "0.0", inclusive = false, message = "price more than 0")
    private BigDecimal price;

    @NotNull(message = "quanity not null")
    @Min(value = 0)
    private int quantity;

    private java.math.BigDecimal discountPercentage;
    private java.math.BigDecimal rating;

    @NotNull(message = "brand not null")
    private Long brand_id;

    @NotNull(message = "category not null")
    private Long category_id;

    @NotNull(message = "deleted not null")
    private int deleted;

    private String imageUrl;
    private java.util.List<ProductVariantDTO> variants;
}

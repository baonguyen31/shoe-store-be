package com.tmdtud.cuahang.api.brand.request;

import java.math.BigDecimal;

import org.aspectj.weaver.ast.Not;
import org.springframework.validation.annotation.Validated;

import com.tmdtud.cuahang.api.brand.model.Brands;
import com.tmdtud.cuahang.api.category.model.Categories;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandUpdateRequest {
    @NotNull(message = "id not null")
    private Long id;

    @NotBlank(message = "name not empty")
    @NotNull(message = "name not null")
    private String name;

    @NotNull(message = "category not null")
    private Long category_id;
}

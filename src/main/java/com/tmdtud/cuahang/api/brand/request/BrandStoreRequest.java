package com.tmdtud.cuahang.api.brand.request;
// Re-saving to trigger build

import java.math.BigDecimal;

import org.springframework.validation.annotation.Validated;

import com.tmdtud.cuahang.api.category.model.Categories;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandStoreRequest {
    @NotBlank(message = "name not empty")
    @NotNull(message = "name not null")
    private String name;

    @NotNull(message = "category not null")
    private Long category_id;
}

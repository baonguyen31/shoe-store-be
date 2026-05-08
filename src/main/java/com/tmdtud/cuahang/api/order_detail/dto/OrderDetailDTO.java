package com.tmdtud.cuahang.api.order_detail.dto;

import java.math.BigDecimal;

import com.tmdtud.cuahang.api.product.dto.ProductSummaryDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDTO{
    private ProductSummaryDTO product;

    private int quantity;

    private BigDecimal cost;

    private BigDecimal total;

    private String color;

    private String size;
}

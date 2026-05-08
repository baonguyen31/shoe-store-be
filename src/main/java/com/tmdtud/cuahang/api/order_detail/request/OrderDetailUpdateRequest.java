package com.tmdtud.cuahang.api.order_detail.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailUpdateRequest {
    @NotNull(message = "id product not null")
    private Long productId;

    @NotNull(message = "quantity not null")
    private int quantity;

    @NotNull(message = "cost not null")
    @DecimalMin(value = "0.0", inclusive = false, message = "cost more than 0")
    private BigDecimal cost;

    @NotNull(message = "cost not null")
    @DecimalMin(value = "0.0", inclusive = false, message = "total more than 0")
    private BigDecimal total;
}

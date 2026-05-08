package com.tmdtud.cuahang.api.order.request;

import com.tmdtud.cuahang.api.order.model.OrderStatus;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderStatusRequest {
    @NotNull(message = "id not null")
    private Long orderId;

    @NotNull(message = "status not null")
    private OrderStatus orderStatusNext;

    @NotNull(message = "id employer not null")
    private Long employerId;
}

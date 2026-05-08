package com.tmdtud.cuahang.api.order.request;


import java.math.BigDecimal;
import java.util.List;

import com.tmdtud.cuahang.api.order_detail.request.OrderDetailStoreRequest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStoreRequest {

    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal totalPrice;

    private Long customerId;

    private String fullName;
    private String phone;
    private String street;
    private String ward;
    private String city;

    private Long employerId;

    @NotBlank(message = "method not empty")
    @NotNull(message = "method not null")
    private String method;

    @NotNull(message = "order_detail not null")
    @Valid
    private List<OrderDetailStoreRequest> details;

}

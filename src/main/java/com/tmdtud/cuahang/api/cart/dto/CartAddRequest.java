package com.tmdtud.cuahang.api.cart.dto;

import lombok.Data;

@Data
public class CartAddRequest {
    private Long productId;
    private int quantity;
    private String size;
    private String color;
}

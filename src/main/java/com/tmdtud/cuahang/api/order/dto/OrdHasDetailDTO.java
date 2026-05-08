package com.tmdtud.cuahang.api.order.dto;

import java.util.List;

import com.tmdtud.cuahang.api.order_detail.dto.OrderDetailDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrdHasDetailDTO {
    private OrderDTO order;
    private List<OrderDetailDTO> details;
}

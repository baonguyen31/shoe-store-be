package com.tmdtud.cuahang.api.order.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.tmdtud.cuahang.api.order.dto.OrdHasDetailDTO;
import com.tmdtud.cuahang.api.order.model.Orders;
import com.tmdtud.cuahang.api.order_detail.mapper.OrderDetailMapper;
import com.tmdtud.cuahang.api.order_detail.model.OrdersDetails;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = { OrderMapper.class,
        OrderDetailMapper.class })
public interface OrderAggregateMapper {
    @Mapping(source = "order", target = "order")
    @Mapping(source = "details", target = "details")
    OrdHasDetailDTO toPurOrdHasDetailDTO(Orders order, List<OrdersDetails> details);
}

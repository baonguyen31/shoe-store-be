package com.tmdtud.cuahang.api.order.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.tmdtud.cuahang.api.order.dto.OrderDTO;
import com.tmdtud.cuahang.api.order.model.Orders;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {
    OrderDTO toDTO(Orders order);

    List<OrderDTO> toDTOList(List<Orders> orders);

    Orders toEntity(OrderDTO order);


}

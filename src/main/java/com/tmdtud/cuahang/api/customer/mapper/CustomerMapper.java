package com.tmdtud.cuahang.api.customer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.tmdtud.cuahang.api.customer.dto.CustomerDTO;
import com.tmdtud.cuahang.api.customer.model.Customers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {
    CustomerDTO toDTO(Customers customer);

    Customers toEntity(CustomerDTO customerDTO);

    @Mapping(target = "password", ignore = true)
    void updateEntityFromDTO(CustomerDTO customerDTO, @MappingTarget Customers entity);
}

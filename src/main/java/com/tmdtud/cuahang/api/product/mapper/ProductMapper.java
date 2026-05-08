package com.tmdtud.cuahang.api.product.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.tmdtud.cuahang.api.product.dto.ProductDTO;
import com.tmdtud.cuahang.api.product.model.Products;



@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {
    // vi diệu ở chỗ mapper tự chuyển đổi ngầm từ en -> dto không cần method, method chỉ để xử lí logic ở trên, quaooo
    ProductDTO toDTO(Products products);
    Products toEntity(ProductDTO product);
}

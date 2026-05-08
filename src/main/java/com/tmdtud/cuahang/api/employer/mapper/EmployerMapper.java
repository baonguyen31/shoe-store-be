package com.tmdtud.cuahang.api.employer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.tmdtud.cuahang.api.employer.dto.EmployerDTO;
import com.tmdtud.cuahang.api.employer.dto.EmployerSummaryDTO;
import com.tmdtud.cuahang.api.employer.model.Employers;



@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployerMapper {
    EmployerDTO toDTO(Employers employer);
    EmployerSummaryDTO toSummaryDTO(Employers employer);
    Employers toEntity(EmployerDTO employerDTO);

    @Mapping(target = "password", ignore = true)
    void updateEntityFromDTO(EmployerDTO dto, @MappingTarget Employers entity);

}

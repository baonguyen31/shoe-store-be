package com.tmdtud.cuahang.api.employer.dto;

import java.math.BigDecimal;

import com.tmdtud.cuahang.common.dto.UserDTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class EmployerDTO extends UserDTO{
    @NotNull(message = "salary not null")
    @DecimalMin(value = "0.0", inclusive = false, message = "salary must be greater than 0")
    private BigDecimal salary;
}

package com.tmdtud.cuahang.api.employer.model;

import java.math.BigDecimal;

import com.tmdtud.cuahang.common.model.Users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Table(name = "employers")
@Entity
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Employers extends Users{

    @Column
    private BigDecimal salary;
    
}

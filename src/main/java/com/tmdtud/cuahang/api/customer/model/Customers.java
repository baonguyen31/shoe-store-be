package com.tmdtud.cuahang.api.customer.model;

import java.math.BigDecimal;

import com.tmdtud.cuahang.common.model.Users;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Table(name = "customers")
@Entity
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Customers extends Users{
    
}

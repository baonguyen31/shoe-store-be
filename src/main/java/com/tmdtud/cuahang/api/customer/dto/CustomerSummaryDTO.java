package com.tmdtud.cuahang.api.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerSummaryDTO {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String city;
    private String ward;
    private String street;
}

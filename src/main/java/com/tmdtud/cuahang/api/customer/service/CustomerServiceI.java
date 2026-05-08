package com.tmdtud.cuahang.api.customer.service;


import org.springframework.data.domain.Pageable;

import com.tmdtud.cuahang.api.customer.dto.CustomerDTO;
import com.tmdtud.cuahang.api.customer.model.Customers;
import com.tmdtud.cuahang.common.response.PageResponse;


public interface CustomerServiceI {

    PageResponse<CustomerDTO> getAll(Pageable pageable);
    
    CustomerDTO getById(Long id); 
    void delete(Long id);       
    
    void add(CustomerDTO customer);
    void update(CustomerDTO customer);
    String generateRandomPassword();
    void handleAdminResetPassword(Long customerId);
}


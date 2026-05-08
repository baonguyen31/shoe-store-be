package com.tmdtud.cuahang.api.customer.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tmdtud.cuahang.api.customer.model.Customers;

@Repository
public interface CustomerRepository extends JpaRepository<Customers, Long> {
    Customers findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}

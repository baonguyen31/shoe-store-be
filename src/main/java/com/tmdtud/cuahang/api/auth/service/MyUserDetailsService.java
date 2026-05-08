package com.tmdtud.cuahang.api.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tmdtud.cuahang.api.auth.model.CustomerDetails;
import com.tmdtud.cuahang.api.auth.model.EmployerDetails;
import com.tmdtud.cuahang.api.customer.model.Customers;
import com.tmdtud.cuahang.api.customer.repository.CustomerRepository;
import com.tmdtud.cuahang.api.employer.model.Employers;
import com.tmdtud.cuahang.api.employer.repository.EmployerRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private EmployerRepository employerRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 1. Kiểm tra Nhân viên trước (Ưu tiên)
        Employers employer = employerRepo.findByEmail(email);
        if (employer != null) {
            if (employer.getStatus() == 0) throw new RuntimeException("Tài khoản nhân viên bị khóa");
            
            // Trả về EmployerDetails kèm Role: ROLE_STAFF
            return new EmployerDetails(employer); 
        }

        // 2. Kiểm tra Khách hàng
        Customers customer = customerRepo.findByEmail(email);
        if (customer != null) {
            if (customer.getStatus() == 0) throw new RuntimeException("Tài khoản khách hàng bị khóa");
            
            // Trả về CustomerDetails kèm Role: ROLE_USER
            return new CustomerDetails(customer);
        }

        throw new UsernameNotFoundException("Không tìm thấy email: " + email);
    }
}
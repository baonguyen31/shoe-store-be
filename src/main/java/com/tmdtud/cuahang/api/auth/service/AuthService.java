package com.tmdtud.cuahang.api.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tmdtud.cuahang.api.customer.model.Customers;
import com.tmdtud.cuahang.api.customer.repository.CustomerRepository;
import com.tmdtud.cuahang.api.employer.model.Employers;
import com.tmdtud.cuahang.api.employer.repository.EmployerRepository;
import com.tmdtud.cuahang.common.model.Users;

@Service
public class AuthService {

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private EmployerRepository employerRepo;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Customers registerCustomer(Customers customer) {
        customer.setPassword(encoder.encode(customer.getPassword()));
        customer.setStatus(1);
        customerRepo.save(customer);
        return customer;
    }

    public Employers registerEmployer(Employers employer) {
        employer.setPassword(encoder.encode(employer.getPassword()));
        employerRepo.save(employer);
        return employer;
    }

    public String verify(Users user) {
        Authentication authentication = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        } else {
            return "fail";
        }
    }
}
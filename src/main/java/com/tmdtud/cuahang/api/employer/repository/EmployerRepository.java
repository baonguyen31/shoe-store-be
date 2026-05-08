package com.tmdtud.cuahang.api.employer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tmdtud.cuahang.api.employer.model.Employers;

@Repository
public interface EmployerRepository extends JpaRepository<Employers, Long> {
    Employers findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);

}

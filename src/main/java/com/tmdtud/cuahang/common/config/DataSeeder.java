package com.tmdtud.cuahang.common.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tmdtud.cuahang.api.employer.model.Employers;
import com.tmdtud.cuahang.api.employer.repository.EmployerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataSeeder {

    private final EmployerRepository employerRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            Employers admin = employerRepository.findByEmail("admin@gmail.com");
            if (admin == null) {
                log.info("Khởi tạo tài khoản Admin mặc định (admin@gmail.com)...");
                admin = new Employers();
                admin.setFullName("Quản trị viên");
                admin.setEmail("admin@gmail.com");
                admin.setUsername("admin@gmail.com");
                admin.setPassword(passwordEncoder.encode("123456"));
                admin.setStatus(1); // Hoạt động
                admin.setSalary(java.math.BigDecimal.valueOf(10000000));

                employerRepository.save(admin);
                log.info("Đã tạo tài khoản: admin@gmail.com / 123456");
            } else {
                log.info("Cập nhật mật khẩu cho admin@gmail.com thành 123456...");
                admin.setPassword(passwordEncoder.encode("123456"));
                admin.setStatus(1);
                employerRepository.save(admin);
            }
        };
    }
}

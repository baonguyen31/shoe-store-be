package com.tmdtud.cuahang.api.auth;

import com.tmdtud.cuahang.api.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tmdtud.cuahang.api.auth.service.AuthService;
import com.tmdtud.cuahang.api.customer.model.Customers;
import com.tmdtud.cuahang.api.employer.model.Employers;
import com.tmdtud.cuahang.common.model.Users;
import com.tmdtud.cuahang.common.response.ApiResponse;

import java.util.Map;

@RestController
@Validated
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService service;

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/register/customers")
    public ApiResponse<Customers> registerCustomer(@Validated @RequestBody Customers customer) {
        return ApiResponse.success(service.registerCustomer(customer));
    }

    @PostMapping("/register/employers")
    public ApiResponse<Employers> registerEmployer(@Validated @RequestBody Employers employer) {
        return ApiResponse.success(service.registerEmployer(employer));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users user) {
        try {
            log.info("Login attempt for user: {}", user.getUsername());

            String token = service.verify(user);

            return ResponseEntity.ok(java.util.Map.of("token", token));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(java.util.Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(java.util.Map.of("message", "Đã xảy ra lỗi hệ thống!"));
        }
    }

    @GetMapping("/api/auth/me")
    public ApiResponse<?> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            return ApiResponse.error(401, "Chưa đăng nhập");
        }
        Object principal = authentication.getPrincipal();
        // Trong AuthController.java, chỗ hàm getCurrentUser
        if (principal instanceof com.tmdtud.cuahang.api.auth.model.CustomerDetails) {
            com.tmdtud.cuahang.api.auth.model.CustomerDetails userDetails = (com.tmdtud.cuahang.api.auth.model.CustomerDetails) principal;
            Customers customer = userDetails.getCustomer();
            java.util.Map<String, Object> data = new java.util.HashMap<>();
            data.put("id", customer.getId());
            data.put("fullName", customer.getFullName());
            data.put("email", customer.getEmail());
            data.put("phone", customer.getPhone());
            data.put("dateOfBirth", customer.getDateOfBirth());
            data.put("city", customer.getCity());
            data.put("ward", customer.getWard());
            data.put("street", customer.getStreet());
            data.put("role", "CUSTOMER");
            return ApiResponse.success(data);
        } else if (principal instanceof com.tmdtud.cuahang.api.auth.model.EmployerDetails) {
            com.tmdtud.cuahang.api.auth.model.EmployerDetails userDetails = (com.tmdtud.cuahang.api.auth.model.EmployerDetails) principal;
            java.util.Map<String, Object> data = new java.util.HashMap<>();
            data.put("id", userDetails.getEmployer().getId());
            data.put("fullName", userDetails.getEmployer().getFullName());
            data.put("email", userDetails.getEmployer().getEmail());
            data.put("role", "STAFF");
            return ApiResponse.success(data);
        }
        return ApiResponse.error(400, "Không nhận diện được người dùng");
    }

    @PatchMapping("/forgot-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> payload) {
        if (payload == null || !payload.containsKey("email")) {
            return ResponseEntity.badRequest().body("Dữ liệu không hợp lệ");
        }

        String email = payload.get("email");

        Customers customer = customerRepository.findByEmail(email);
        if (customer == null) {
            return ResponseEntity.status(404).body("Email không tồn tại");
        }

        customer.setResetRequested(true);
        customerRepository.save(customer);

        return ResponseEntity.ok("Yêu cầu đã được gửi tới Admin. Vui lòng chờ email.");

    }
}
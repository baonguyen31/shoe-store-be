package com.tmdtud.cuahang.api.customer;

import com.tmdtud.cuahang.api.customer.model.Customers;
import com.tmdtud.cuahang.api.customer.repository.CustomerRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.tmdtud.cuahang.api.customer.dto.CustomerDTO;
import com.tmdtud.cuahang.api.customer.service.CustomerService;
import com.tmdtud.cuahang.common.construct.BaseController;
import com.tmdtud.cuahang.common.response.ApiResponse;
import com.tmdtud.cuahang.common.response.PageResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/customers")
@Validated
@RequiredArgsConstructor
public class CustomerController extends BaseController {

    private final CustomerService customerService;


    // 1. LẤY DANH SÁCH (Sửa lỗi return null)
    @GetMapping
    public ApiResponse<PageResponse<CustomerDTO>> getAll(
        @RequestParam(value = "page_no", defaultValue = "0") int page,
        @RequestParam(value = "page_size", defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "asc") String sortDir
    ){
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) 
                    ? Sort.by(sortBy).ascending() 
                    : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        // Trả về dữ liệu thật từ service thay vì null
        return ApiResponse.success(customerService.getAll(pageable));
    }

    // 2. LẤY THEO ID
    @GetMapping("/{id}")
    public ApiResponse<CustomerDTO> getById(@PathVariable Long id) {
        return ApiResponse.success(customerService.getById(id));
    }

    // 3. ĐĂNG KÝ (Dành cho khách hàng tự đăng ký)
    @PostMapping("/register/customers")
    public ResponseEntity<?> register(@RequestBody CustomerDTO customerDTO) {
        try {
            customerService.add(customerDTO);
            return ResponseEntity.ok("Đăng ký thành công");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 4. THÊM MỚI (Dành cho Admin gọi từ trang quản lý)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<String> add(@RequestBody CustomerDTO customerDTO) {
        customerService.add(customerDTO);
        return ApiResponse.success("Thêm khách hàng thành công!");
    }

    // 5. CẬP NHẬT & KHÓA/MỞ KHÓA (Quan trọng để nút trên UI hoạt động)
    @PutMapping("/{id}")
    public ApiResponse<String> update(@PathVariable Long id, @RequestBody CustomerDTO dto) {
        dto.setId(id);
        customerService.update(dto); // Hàm này sẽ xử lý cả việc đổi Status 1 <-> 0
        return ApiResponse.success("Cập nhật khách hàng thành công!");
    }



//    @PatchMapping("/forgot-password")
//    public ResponseEntity<?> resetPassword(@RequestParam String email) {
//        Customers customer = customerRepository.findByEmail(email);
//
//        customer.setResetRequested(true);
//        customerRepository.save(customer);
//
//        return ResponseEntity.ok("Yêu cầu đã được gửi tới Admin. Vui lòng chờ email.");
//
//    }

    @PutMapping("/{id}/reset-password")
        public ResponseEntity<?> resetPassword(@PathVariable Long id) {
            customerService.handleAdminResetPassword(id);

            return ResponseEntity.ok("Đã reset và gửi mail thành công");
        }

}
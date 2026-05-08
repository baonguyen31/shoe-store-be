package com.tmdtud.cuahang.api.customer.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tmdtud.cuahang.api.customer.dto.CustomerDTO;
import com.tmdtud.cuahang.api.customer.mapper.CustomerMapper;
import com.tmdtud.cuahang.api.customer.model.Customers;
import com.tmdtud.cuahang.api.customer.repository.CustomerRepository;
import com.tmdtud.cuahang.common.response.PageResponse;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@Data
@RequiredArgsConstructor
public class CustomerService implements CustomerServiceI {

    private final CustomerRepository customer;
    private final CustomerMapper customerMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    private final JavaMailSender mailSender;



    @Override
    public PageResponse<CustomerDTO> getAll(Pageable pageable) {
        Page<Customers> customers = customer.findAll(pageable);
        return new PageResponse<CustomerDTO>(customers.map(customer -> customerMapper.toDTO(customer)));
    }

@Override
    public CustomerDTO getById(Long id) {
        // Tìm khách hàng theo ID, nếu không thấy thì ném lỗi
        Customers customerEntity = customer.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng với ID: " + id));
        
        // Chuyển đổi Entity sang DTO bằng Mapper
        return customerMapper.toDTO(customerEntity);
    }

    @Override
    public void delete(Long id) {
        if (!customer.existsById(id)) {
            throw new RuntimeException("Không tìm thấy khách hàng để xóa");
        }
        customer.deleteById(id);
    }

    @Override
    public void add(CustomerDTO customerDTO) {
        if (customer.existsByEmail(customerDTO.getEmail())) {
            throw new RuntimeException("Email này đã được sử dụng!");
        }
        if (customer.existsByPhone(customerDTO.getPhone())) {
            throw new RuntimeException("Số điện thoại này đã được đăng ký!");
        }

        Customers entity = customerMapper.toEntity(customerDTO);
        entity.setStatus(1);
        entity.setPassword(passwordEncoder.encode(customerDTO.getPassword()));

        customer.save(entity);
    }

   @Override
    public void update(CustomerDTO customerDTO) {
        // 1. Tìm khách hàng cũ trong DB
        Customers existing = customer.findById(customerDTO.getId())
                .orElseThrow(() -> new RuntimeException("ID khách hàng không tồn tại: " + customerDTO.getId()));

        // 2. Cập nhật các thông tin cơ bản
        existing.setFullName(customerDTO.getFullName());
        existing.setPhone(customerDTO.getPhone());
        existing.setCity(customerDTO.getCity());
        existing.setWard(customerDTO.getWard());
        existing.setStreet(customerDTO.getStreet());
        existing.setDateOfBirth(customerDTO.getDateOfBirth());
        
        // 3. Quan trọng: Chỉ cập nhật mật khẩu nếu FE có gửi mật khẩu mới lên
        if (customerDTO.getPassword() != null && !customerDTO.getPassword().trim().isEmpty()) {
            // Mã hóa mật khẩu mới trước khi lưu
            String encodedPassword = passwordEncoder.encode(customerDTO.getPassword());
            existing.setPassword(encodedPassword);
            System.out.println("Đã cập nhật mật khẩu mới cho user: " + existing.getEmail());
        } else {
            // Nếu mật khẩu gửi lên trống, giữ nguyên mật khẩu cũ trong DB
            System.out.println("Không thay đổi mật khẩu cho user: " + existing.getEmail());
        }

        // 4. Giữ nguyên trạng thái (Status)
        if (customerDTO.getStatus() != null) {
            existing.setStatus(customerDTO.getStatus());
        }

        // 5. Lưu lại vào DB
        customer.save(existing);
    }

    @Override
    public String generateRandomPassword() {
        String chars = "abcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder sb= new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }

    @Override
    @Transactional
    public void handleAdminResetPassword(Long customerId) {
        Customers custoMer = customer.findById(customerId)
                .orElseThrow(() -> new RuntimeException("ID khách hàng không tồn tại"));

        String rawPassword = generateRandomPassword();
        custoMer.setPassword(passwordEncoder.encode(rawPassword));
        custoMer.setResetRequested(false);
        customer.save(custoMer);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(custoMer.getEmail());
        message.setSubject("Mật khẩu của bạn đã được đặt lại");
        message.setText("Chào " + custoMer.getFullName() + ",\n\n" +
                "Theo yêu cầu, mật khẩu của bạn đã được reset.\n" +
                "Mật khẩu mới là: " + rawPassword + "\n\n" +
                "Vui lòng dùng mật khẩu này để đăng nhập và đổi lại mật khẩu mới.");

        mailSender.send(message);


    }


}

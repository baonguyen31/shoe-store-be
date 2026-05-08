package com.tmdtud.cuahang.api.employer.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import com.tmdtud.cuahang.api.employer.dto.EmployerDTO;
import com.tmdtud.cuahang.api.employer.mapper.EmployerMapper;
import com.tmdtud.cuahang.api.employer.model.Employers;
import com.tmdtud.cuahang.api.employer.repository.EmployerRepository;
import com.tmdtud.cuahang.common.response.PageResponse;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@Data
@RequiredArgsConstructor
public class EmployerService implements EmployerServiceI {

    private final EmployerRepository employer;
    private final EmployerMapper employerMapper;
    // Thêm PasswordEncoder để mã hóa mật khẩu trước khi lưu
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @Override
    public PageResponse<EmployerDTO> getAll(Pageable pageable) {
        Page<Employers> employersPage = employer.findAll(pageable);
        return new PageResponse<EmployerDTO>(employersPage.map(emp -> employerMapper.toDTO(emp)));
    }

    @Override
    public void add(EmployerDTO dto) {
        // 1. Kiểm tra xem email đã tồn tại chưa
        if (employer.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email nhân viên đã tồn tại!");
        }

        // 2. Chuyển DTO sang Entity
        Employers newEmployer = employerMapper.toEntity(dto);

        // 3. Mã hóa mật khẩu (mặc định hoặc từ form)
        // Nếu admin không nhập, ta có thể để mặc định là '123456'
        String rawPassword = (dto.getPassword() != null && !dto.getPassword().isEmpty()) 
                             ? dto.getPassword() : "123456";
        newEmployer.setPassword(passwordEncoder.encode(rawPassword));

        // 4. Mặc định trạng thái là hoạt động (1)
        newEmployer.setStatus(1);

        employer.save(newEmployer);
    }

    @Override
    public void update(EmployerDTO dto) {
        // 1. Tìm nhân viên cũ từ Database
        Employers existing = employer.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên!"));

        employerMapper.updateEntityFromDTO(dto, existing);
        
        if (dto.getSalary() != null) {
            existing.setSalary(dto.getSalary());
        }

        employer.save(existing);
    }

    @Override
    public void delete(Long id) {
        // Thay vì xóa cứng, ta nên chuyển status = 0 (Xóa mềm)
        employer.findById(id).ifPresent(emp -> {
            emp.setStatus(0);
            employer.save(emp);
        });
    }

    @Override
    public Employers getById(Long id) {
        return employer.findById(id).orElse(null);
    }
}
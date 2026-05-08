package com.tmdtud.cuahang.api.employer;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.tmdtud.cuahang.api.employer.dto.EmployerDTO;
import com.tmdtud.cuahang.api.employer.service.EmployerService;
import com.tmdtud.cuahang.common.construct.BaseController;
import com.tmdtud.cuahang.common.response.ApiResponse;
import com.tmdtud.cuahang.common.response.PageResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/employers")
@RequiredArgsConstructor
public class EmployerController extends BaseController {

    private final EmployerService employerService;

    @GetMapping
    public ApiResponse<PageResponse<EmployerDTO>> getAll(
            @RequestParam(value = "page_no", defaultValue = "0") int page,
            @RequestParam(value = "page_size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return ApiResponse.success(employerService.getAll(pageable));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<String> add(@RequestBody EmployerDTO dto) {
        employerService.add(dto);
        return ApiResponse.success("Thêm nhân viên mới thành công!");
    }

    @PutMapping("/{id}")
    public ApiResponse<String> update(@PathVariable Long id, @RequestBody EmployerDTO dto) {
        dto.setId(id);
        employerService.update(dto);
        return ApiResponse.success("Cập nhật thành công!");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        employerService.delete(id);
        return ApiResponse.success("Đã khóa tài khoản nhân viên!");
    }
}
package com.tmdtud.cuahang.api.brand;
// Re-saving to trigger build

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tmdtud.cuahang.api.brand.dto.BrandDTO;
import com.tmdtud.cuahang.api.brand.mapper.BrandMapper;
import com.tmdtud.cuahang.api.brand.model.Brands;
import com.tmdtud.cuahang.api.brand.request.BrandStoreRequest;
import com.tmdtud.cuahang.api.brand.request.BrandUpdateRequest;
import com.tmdtud.cuahang.api.brand.service.BrandService;
import com.tmdtud.cuahang.common.response.ApiResponse;
import com.tmdtud.cuahang.common.response.PageResponse;

@RequestMapping("api/brands")
@RestController
@Validated
public class BrandController {
    @Autowired
    private BrandService brandService;
    
    @Autowired
    private BrandMapper brandMapper;

    @GetMapping
    public ApiResponse<PageResponse<BrandDTO>> getAll(
            @RequestParam(value = "page_no", defaultValue = "0") int page,
            @RequestParam(value = "page_size", defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        PageResponse<Brands> pageResponse = brandService.getAll(pageable);
        List<BrandDTO> brands = pageResponse.getContent()
                    .stream()
                    .map(item -> brandMapper.toDTO(item))
                    .collect(Collectors.toList());
        PageResponse<BrandDTO> pageResponse2 = PageResponse.<BrandDTO>builder()
                                                .content(brands)
                                                .num(pageResponse.getNum())
                                                .size(pageResponse.getSize())
                                                .total(pageResponse.getTotal()).build();
                    
        return ApiResponse.success(pageResponse2);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Boolean> delete(@PathVariable Long id){
        return ApiResponse.success(brandService.delete(id));
    }

    @PostMapping()
    public ApiResponse<BrandDTO> add(@Validated @RequestBody BrandStoreRequest request){
        return ApiResponse.success(brandMapper.toDTO(brandService.add(request)));
    }

    @GetMapping("/{id}")
    public ApiResponse<BrandDTO> getById(@PathVariable Long id){
        return ApiResponse.success(brandMapper.toDTO(brandService.getById(id)));
    }

    @PutMapping()
    public ApiResponse<BrandDTO> update(@Validated @RequestBody BrandUpdateRequest request){
        return ApiResponse.success(brandMapper.toDTO(brandService.update(request)));
    } 
}

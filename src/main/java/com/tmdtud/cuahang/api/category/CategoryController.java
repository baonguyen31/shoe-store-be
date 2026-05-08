package com.tmdtud.cuahang.api.category;

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

import com.tmdtud.cuahang.api.category.dto.CategoryDTO;
import com.tmdtud.cuahang.api.category.mapper.CategoryMapper;
import com.tmdtud.cuahang.api.category.model.Categories;
import com.tmdtud.cuahang.api.category.request.CategoryStoreRequest;
import com.tmdtud.cuahang.api.category.request.CategoryUpdateRequest;
import com.tmdtud.cuahang.api.category.service.CategoryService;
import com.tmdtud.cuahang.common.response.ApiResponse;
import com.tmdtud.cuahang.common.response.PageResponse;

import lombok.RequiredArgsConstructor;

@RequestMapping("api/categories")
@RestController
@Validated
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryMapper categoryMapper;

    @GetMapping
    public ApiResponse<PageResponse<CategoryDTO>> getAll(
        @RequestParam(value = "page_no", defaultValue = "0") int page,
        @RequestParam(value = "page_size", defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "asc") String sortDir
    ){
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
            ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        
        Pageable pageable = PageRequest.of(page, size, sort);

        PageResponse<Categories> pageResponse = categoryService.getAll(pageable);
        List<CategoryDTO> categoryDTOs = pageResponse
                                            .getContent()
                                            .stream()
                                            .map(item -> categoryMapper.toDTO(item))
                                            .collect(Collectors.toList());
        PageResponse<CategoryDTO> pageResponse2 = PageResponse.<CategoryDTO>builder()
                                                    .content(categoryDTOs)
                                                    .size(pageResponse.getSize())
                                                    .total(pageResponse.getTotal())
                                                    .num(pageResponse.getNum()).build();

        return ApiResponse.success(pageResponse2);
    }

    @PostMapping()
    public ApiResponse<CategoryDTO> add(@Validated @RequestBody CategoryStoreRequest request){
        return ApiResponse.success(categoryMapper.toDTO(categoryService.add(request)));
    }

    @GetMapping("/{id}")
    public ApiResponse<CategoryDTO> getById(@PathVariable Long id){
        return ApiResponse.success(categoryMapper.toDTO(categoryService.getById(id)));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Boolean> delete(@PathVariable Long id){
        return ApiResponse.success(categoryService.delete(id));
    }

    @PutMapping()
    public ApiResponse<CategoryDTO> update(@Validated @RequestBody CategoryUpdateRequest request){
        return ApiResponse.success(categoryMapper.toDTO(categoryService.update(request)));
    }
}

package com.tmdtud.cuahang.api.product;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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

import com.tmdtud.cuahang.api.product.dto.ProductDTO;
import com.tmdtud.cuahang.api.product.mapper.ProductMapper;
import com.tmdtud.cuahang.api.product.model.Products;
import com.tmdtud.cuahang.api.product.request.ProductStoreRequest;
import com.tmdtud.cuahang.api.product.request.ProductUpdateRequest;
import com.tmdtud.cuahang.api.product.service.ProductService;
import com.tmdtud.cuahang.common.construct.BaseController;
import com.tmdtud.cuahang.common.response.ApiResponse;
import com.tmdtud.cuahang.common.response.PageResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/products")
@Validated
@RequiredArgsConstructor
public class ProductController extends BaseController {

    private final ProductService productSer;
    private final ProductMapper productMapper;

    @GetMapping
    public ApiResponse<PageResponse<ProductDTO>> getAll(
            @RequestParam(value = "page_no", defaultValue = "0") int page,
            @RequestParam(value = "page_size", defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long category_id,
            @RequestParam(required = false) Long brand_id,
            @RequestParam(required = false) BigDecimal min_price,
            @RequestParam(required = false) BigDecimal max_price,
            @RequestParam(required = false) String color) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        
        PageResponse<Products> pageResponse = productSer.getAll(name, category_id, brand_id, min_price, max_price, color, pageable);
        List<ProductDTO> productDTOs = pageResponse.getContent()
                                        .stream()
                                        .map(item -> productMapper.toDTO(item))
                                        .collect(Collectors.toList());
        PageResponse<ProductDTO> pageResponse2 = PageResponse.<ProductDTO>builder()
                                                    .content(productDTOs)
                                                    .num(pageResponse.getNum())
                                                    .size(pageResponse.getSize())
                                                    .total(pageResponse.getTotal()).build();

        return ApiResponse.success(pageResponse2);
    }

    @GetMapping("/best-sellers")
    public ApiResponse<PageResponse<ProductDTO>> getBestSellers(
            @RequestParam(value = "page_no", defaultValue = "0") int page,
            @RequestParam(value = "page_size", defaultValue = "10") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long category_id,
            @RequestParam(required = false) Long brand_id,
            @RequestParam(required = false) BigDecimal min_price,
            @RequestParam(required = false) BigDecimal max_price,
            @RequestParam(required = false) String color) {
        Pageable pageable = PageRequest.of(page, size);
        
        PageResponse<Products> pageResponse = productSer.getBestSellers(name, category_id, brand_id, min_price, max_price, color, pageable);
        List<ProductDTO> productDTOs = pageResponse.getContent()
                                        .stream()
                                        .map(item -> productMapper.toDTO(item))
                                        .collect(Collectors.toList());
        PageResponse<ProductDTO> pageResponse2 = PageResponse.<ProductDTO>builder()
                                                    .content(productDTOs)
                                                    .num(pageResponse.getNum())
                                                    .size(pageResponse.getSize())
                                                    .total(pageResponse.getTotal()).build();

        return ApiResponse.success(pageResponse2);
    }

    @PostMapping()
    public ApiResponse<ProductDTO> add(@Validated @RequestBody ProductStoreRequest product){
        return ApiResponse.success(productMapper.toDTO(productSer.add(product)));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Boolean> delete(@PathVariable Long id){
        return ApiResponse.success(productSer.delete(id));
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductDTO> getById(@PathVariable Long id){
        return ApiResponse.success(productMapper.toDTO(productSer.getById(id)));
    }

    @PutMapping()
    public ApiResponse<ProductDTO> update(@Validated @RequestBody ProductUpdateRequest request){
        return ApiResponse.success(productMapper.toDTO(productSer.update(request)));
    }
}

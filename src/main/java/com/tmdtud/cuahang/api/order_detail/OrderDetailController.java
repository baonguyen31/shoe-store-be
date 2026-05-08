package com.tmdtud.cuahang.api.order_detail;

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

import com.tmdtud.cuahang.api.order_detail.dto.OrderDetailDTO;
import com.tmdtud.cuahang.api.order_detail.mapper.OrderDetailMapper;
import com.tmdtud.cuahang.api.order_detail.model.OrdersDetails;
import com.tmdtud.cuahang.api.order_detail.request.OrderDetailStoreRequest;
import com.tmdtud.cuahang.api.order_detail.request.OrderDetailUpdateRequest;
import com.tmdtud.cuahang.api.order_detail.service.OrderDetailService;
import com.tmdtud.cuahang.common.construct.BaseController;
import com.tmdtud.cuahang.common.response.ApiResponse;
import com.tmdtud.cuahang.common.response.PageResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/ordersDetails")
@Validated
@RequiredArgsConstructor
public class OrderDetailController extends BaseController {

    private final OrderDetailService orderDetailService;
    private final OrderDetailMapper orderDetailMapper;

    @GetMapping
    public ApiResponse<PageResponse<OrderDetailDTO>> getAll(
            @RequestParam(value = "page_no", defaultValue = "0") int page,
            @RequestParam(value = "page_size", defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        PageResponse<OrdersDetails> pageResponse = orderDetailService.getAll(pageable);
        List<OrdersDetails> ordersDetails = pageResponse.getContent();

        List<OrderDetailDTO> orderDetailDTOs = ordersDetails.stream()
                .map(item -> orderDetailMapper.toDTO(item)).collect(Collectors.toList());
        PageResponse<OrderDetailDTO> pageResponse2 = PageResponse.<OrderDetailDTO>builder()
                .content(orderDetailDTOs)
                .num(pageResponse.getNum())
                .size(pageResponse.getSize())
                .total(pageResponse.getTotal()).build();

        return ApiResponse.success(pageResponse2);
    }

    @PostMapping()
    public ApiResponse<OrderDetailDTO> add(@Validated @RequestBody OrderDetailStoreRequest request) {
        return ApiResponse.success(orderDetailMapper.toDTO(orderDetailService.add(request)));
    }

    @DeleteMapping("/{orderDetailId}/{productId}")
    public ApiResponse<Boolean> delete(@PathVariable Long puchase, @PathVariable Long product) {
        return ApiResponse.success(orderDetailService.delete(puchase, product));
    }

    @PutMapping()
    public ApiResponse<Boolean> update(@Validated @RequestBody OrderDetailUpdateRequest request) {
        return ApiResponse.success(true);
    }

    @GetMapping("/{orderDetailId}/{productId}")
    public ApiResponse<OrderDetailDTO> getById(@PathVariable Long orderId, @PathVariable Long productId) {
        return ApiResponse
                .success(orderDetailMapper.toDTO(orderDetailService.getById(orderId, productId)));
    }

}

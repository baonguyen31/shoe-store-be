package com.tmdtud.cuahang.api.order.service;

import java.time.LocalDate;

import org.springframework.data.domain.Pageable;

import com.tmdtud.cuahang.api.order.dto.OrdHasDetailDTO;
import com.tmdtud.cuahang.api.order.model.Orders;
import com.tmdtud.cuahang.api.order.request.OrderStoreRequest;
import com.tmdtud.cuahang.api.order.request.OrderUpdateRequest;
import com.tmdtud.cuahang.api.order.request.UpdateOrderStatusRequest;
import com.tmdtud.cuahang.common.response.PageResponse;

public interface OrderServiceI {
    public PageResponse<Orders> getAll(Pageable pageable);

    public Orders getById(Long id);

    public Orders delete(Long id);

    public Orders add(OrderStoreRequest request);

    public Orders update(OrderUpdateRequest request);

    public OrdHasDetailDTO toOrdHasDetailDTO(Orders orders);

    public Orders updateStatus(UpdateOrderStatusRequest request) throws Exception;

    public PageResponse<Orders> getAllByDateRange(String fromDate, String toDate, String status, Pageable pageable);

    public PageResponse<Orders> getMyOrders(Long customerId, Pageable pageable);
}

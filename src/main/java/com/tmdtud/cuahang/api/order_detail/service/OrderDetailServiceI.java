package com.tmdtud.cuahang.api.order_detail.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.tmdtud.cuahang.api.order_detail.model.OrdersDetails;
import com.tmdtud.cuahang.api.order_detail.request.OrderDetailStoreRequest;
import com.tmdtud.cuahang.api.order_detail.request.OrderDetailUpdateRequest;
import com.tmdtud.cuahang.common.response.PageResponse;

public interface OrderDetailServiceI {
    public PageResponse<OrdersDetails> getAll(Pageable pageable);

    public OrdersDetails getById(Long order, Long product);

    public Boolean delete(Long order, Long product);

    public OrdersDetails add(OrderDetailStoreRequest request);

    public OrdersDetails update(OrderDetailUpdateRequest request, Long orderId);

    public List<OrdersDetails> addAll(List<OrderDetailStoreRequest> requests, Long orderId);

    public List<OrdersDetails> getByOrderId(Long id);

    public List<OrdersDetails> updateAll(List<OrderDetailUpdateRequest> requests, Long orderId);

    public int deleteByOrder(Long id);
}

package com.tmdtud.cuahang.api.order_detail.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tmdtud.cuahang.api.order.model.Orders;
import com.tmdtud.cuahang.api.order.service.OrderService;
import com.tmdtud.cuahang.api.order_detail.model.OrdersDetails;
import com.tmdtud.cuahang.api.order_detail.model.OrdersDetailsId;
import com.tmdtud.cuahang.api.order_detail.repository.OrderDetailRepository;
import com.tmdtud.cuahang.api.order_detail.request.OrderDetailStoreRequest;
import com.tmdtud.cuahang.api.order_detail.request.OrderDetailUpdateRequest;
import com.tmdtud.cuahang.api.product.model.Products;
import com.tmdtud.cuahang.api.product.service.ProductService;

import com.tmdtud.cuahang.common.response.PageResponse;

import lombok.Data;

@Service
@Data
public class OrderDetailService implements OrderDetailServiceI {

    @Autowired
    private OrderDetailRepository orderDetailRepo;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Override
    public PageResponse<OrdersDetails> getAll(Pageable pageable) {
        Page<OrdersDetails> purchase_orders_details = orderDetailRepo.findAll(pageable);
        return new PageResponse<OrdersDetails>(purchase_orders_details);
    }

    @Override
    public OrdersDetails add(OrderDetailStoreRequest request) {
        Products products = productService.getById(request.getProductId());

        OrdersDetails purchase = OrdersDetails.builder()
                .cost(request.getCost())
                .total(request.getTotal())
                .product(products)
                .quantity(request.getQuantity()).build();
        return orderDetailRepo.save(purchase);
    }

    @Override
    public Boolean delete(Long purchase_order_id, Long product_id) {
        orderDetailRepo.deleteByBothId(purchase_order_id, product_id);
        return true;
    }

    @Override
    public OrdersDetails getById(Long purchase, Long product) {
        return orderDetailRepo.getByBothId(purchase, product);
    }

    @Override
    public OrdersDetails update(OrderDetailUpdateRequest request, Long orderId) {
        OrdersDetails ordersDetails = orderDetailRepo
                .getByBothId(orderId, request.getProductId());

        ordersDetails.setCost(request.getCost());
        ordersDetails.setQuantity(request.getQuantity());
        ordersDetails.setTotal(request.getTotal());
        return orderDetailRepo.save(ordersDetails);
    }

    @Override
    public List<OrdersDetails> addAll(List<OrderDetailStoreRequest> requests, Long orderId) {
        List<OrdersDetails> oderDetailList = requests
                .stream()
                .map((item) -> {
                    Products product = productService.getById(item.getProductId());
                    Orders orders = orderService.getById(orderId);
                    OrdersDetailsId id = new OrdersDetailsId(item.getProductId(), orderId);

                    OrdersDetails _new = OrdersDetails.builder()
                            .product(product)
                            .order(orders)
                            .quantity(item.getQuantity())
                            .id(id)
                            .cost(item.getCost())
                            .color(item.getColor())
                            .size(item.getSize())
                            .total(item.getTotal()).build();
                    return _new;
                })
                .collect(Collectors.toList());
        return new ArrayList<OrdersDetails>(orderDetailRepo.saveAll(oderDetailList));
    }

    @Override
    public List<OrdersDetails> getByOrderId(Long id) {
        return orderDetailRepo.getByOrderId(id);
    }

    @Override
    public List<OrdersDetails> updateAll(List<OrderDetailUpdateRequest> requests, Long orderId) {
        List<OrdersDetails> orderDetailList = requests
                .stream()
                .map((item) -> {
                    Products product = productService.getById(item.getProductId());
                    Orders orders = orderService.getById(orderId);
                    OrdersDetailsId ordersDetailsId = OrdersDetailsId.builder()
                            .productId(item.getProductId())
                            .orderId(orderId)
                            .build();
                    OrdersDetails _new = OrdersDetails.builder()
                            .product(product)
                            .order(orders)
                            .quantity(item.getQuantity())
                            .id(ordersDetailsId)
                            .cost(item.getCost())
                            .total(item.getTotal()).build();
                    return _new;
                })
                .collect(Collectors.toList());
        return new ArrayList<OrdersDetails>(orderDetailRepo.saveAll(orderDetailList));
    }

    @Override
    public int deleteByOrder(Long id) {
        return orderDetailRepo.deleteByOrder(id);
    }
}

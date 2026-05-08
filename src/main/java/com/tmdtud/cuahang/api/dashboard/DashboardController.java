package com.tmdtud.cuahang.api.dashboard;

import com.tmdtud.cuahang.api.dashboard.dto.DashboardDTO;
import com.tmdtud.cuahang.api.order.model.OrderStatus;
import com.tmdtud.cuahang.api.order.repository.OrderRepository;
import com.tmdtud.cuahang.common.response.ApiResponse;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping
    public ApiResponse<DashboardDTO> getStats(
            @RequestParam(required = false) String fromDate,
            @RequestParam(required = false) String toDate) {
        
        LocalDate from = (fromDate != null && !fromDate.isEmpty()) ? LocalDate.parse(fromDate) : null;
        LocalDate to = (toDate != null && !toDate.isEmpty()) ? LocalDate.parse(toDate) : null;

        DashboardDTO.OrderStatsDTO orders = DashboardDTO.OrderStatsDTO.builder()
                .total(orderRepository.countOrders(from, to, null))
                .success(orderRepository.countOrders(from, to, OrderStatus.DELIVERED))
                .pending(orderRepository.countOrders(from, to, OrderStatus.PENDING) 
                         + orderRepository.countOrders(from, to, OrderStatus.CONFIRMED)
                         + orderRepository.countOrders(from, to, OrderStatus.SHIPPING))
                .cancelled(orderRepository.countOrders(from, to, OrderStatus.CANCELLED))
                .build();

        DashboardDTO.RevenueStatsDTO revenue = DashboardDTO.RevenueStatsDTO.builder()
                .total(orderRepository.sumRevenue(from, to, null))
                .success(orderRepository.sumRevenue(from, to, OrderStatus.DELIVERED))
                .pending(orderRepository.sumRevenue(from, to, OrderStatus.PENDING)
                         .add(orderRepository.sumRevenue(from, to, OrderStatus.CONFIRMED))
                         .add(orderRepository.sumRevenue(from, to, OrderStatus.SHIPPING)))
                .cancelled(orderRepository.sumRevenue(from, to, OrderStatus.CANCELLED))
                .build();

        return ApiResponse.success(new DashboardDTO(orders, revenue));
    }
}

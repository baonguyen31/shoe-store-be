package com.tmdtud.cuahang.api.dashboard.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardDTO {
    private OrderStatsDTO orders;
    private RevenueStatsDTO revenue;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderStatsDTO {
        private long total;
        private long success;
        private long pending;
        private long cancelled;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RevenueStatsDTO {
        private BigDecimal total;
        private BigDecimal success;
        private BigDecimal pending;
        private BigDecimal cancelled;
    }
}

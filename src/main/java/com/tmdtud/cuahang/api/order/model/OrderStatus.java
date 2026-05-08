package com.tmdtud.cuahang.api.order.model;

/**
 * Vòng đời đơn hàng (một chiều, không thể lùi):
 *   PENDING → CONFIRMED → SHIPPING → DELIVERED
 *
 * Huỷ được:  PENDING, CONFIRMED
 * Không huỷ: SHIPPING, DELIVERED, CANCELLED (terminal)
 */
public enum OrderStatus {
    PENDING,
    CONFIRMED,
    SHIPPING,
    DELIVERED,
    CANCELLED;

    /**
     * Admin chỉ được chuyển theo đúng thứ tự tiến.
     * CANCELLED xử lý riêng qua adminCancelOrder().
     */
    public boolean canAdvanceTo(OrderStatus next) {
        return switch (this) {
            case PENDING   -> next == CONFIRMED;
            case CONFIRMED -> next == SHIPPING;
            case SHIPPING  -> next == DELIVERED;
            default        -> false;   // DELIVERED, CANCELLED: terminal
        };
    }

    /** Cả khách lẫn admin đều dùng rule này để huỷ */
    public boolean isCancellable() {
        return this == PENDING || this == CONFIRMED;
    }

    /** Đơn đã kết thúc (không thể thay đổi) */
    public boolean isTerminal() {
        return this == DELIVERED || this == CANCELLED;
    }
}

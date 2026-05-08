package com.tmdtud.cuahang.api.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.tmdtud.cuahang.api.order.model.OrderStatus;
import com.tmdtud.cuahang.api.order.model.Orders;
import com.tmdtud.cuahang.api.order.service.OrderService;
import com.tmdtud.cuahang.common.config.MomoConfig;
import com.tmdtud.cuahang.common.response.ApiResponse;
import com.tmdtud.cuahang.common.service.SseService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private SseService sseService;

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/momo/create_payment")
    public ApiResponse<Map<String, Object>> createMomoPayment(@RequestParam Long orderId) {
        Orders order = orderService.getById(orderId);
        if (order == null) {
            return ApiResponse.error(404, "Đơn hàng không tồn tại");
        }

        // Tạo requestId và orderId duy nhất (Alphanumeric chỉ bao gồm chữ và số)
        String requestId = UUID.randomUUID().toString().replace("-", "");
        String orderReference = order.getId() + "TS" + System.currentTimeMillis();
        String orderInfo = "Thanh toan don hang " + order.getId(); 
        long amount = order.getTotalPrice().longValue();
        String extraData = ""; 

        // Chữ ký thô (BẮT BUỘC Sắp xếp theo thứ tự A-Z của Key theo chuẩn MoMo API v2)
        String rawSignature = "accessKey=" + MomoConfig.ACCESS_KEY
                + "&amount=" + amount
                + "&extraData=" + extraData
                + "&ipnUrl=" + MomoConfig.NOTIFY_URL
                + "&orderId=" + orderReference
                + "&orderInfo=" + orderInfo
                + "&partnerCode=" + MomoConfig.PARTNER_CODE
                + "&redirectUrl=" + MomoConfig.REDIRECT_URL
                + "&requestId=" + requestId
                + "&requestType=captureWallet";

        System.out.println("MoMo Debug - Raw Signature: " + rawSignature);
        String signature = MomoConfig.hmacSha256(rawSignature, MomoConfig.SECRET_KEY);

        // Tạo Request Body
        Map<String, Object> requestBody = new java.util.LinkedHashMap<>();
        requestBody.put("partnerCode", MomoConfig.PARTNER_CODE);
        requestBody.put("requestId", requestId);
        requestBody.put("amount", amount);
        requestBody.put("orderId", orderReference);
        requestBody.put("orderInfo", orderInfo);
        requestBody.put("redirectUrl", MomoConfig.REDIRECT_URL);
        requestBody.put("ipnUrl", MomoConfig.NOTIFY_URL);
        requestBody.put("extraData", extraData);
        requestBody.put("requestType", "captureWallet");
        requestBody.put("signature", signature);
        requestBody.put("lang", "vi");

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(MomoConfig.ENDPOINT, requestBody, Map.class);
            Map<String, Object> responseBody = response.getBody();
            System.out.println("MoMo Debug - Response: " + responseBody);

            if (responseBody != null && responseBody.get("payUrl") != null) {
                Map<String, Object> result = new HashMap<>();
                result.put("qrUrl", responseBody.get("payUrl"));
                result.put("orderId", order.getId());
                result.put("amount", order.getTotalPrice());
                result.put("phone", MomoConfig.MOMO_PHONE);
                result.put("accountName", MomoConfig.MOMO_ACCOUNT_NAME);
                return ApiResponse.success(result);
            } else {
                String errorMsg = responseBody != null && responseBody.get("message") != null 
                                  ? responseBody.get("message").toString() 
                                  : "Unknown Error";
                return ApiResponse.error(500, "MoMo Error: " + errorMsg);
            }
        } catch (Exception e) {
            return ApiResponse.error(500, "Connection Error: " + e.getMessage());
        }
    }

    /**
     * Endpoint nhận thông báo Webhook (IPN) từ MoMo
     */
    @PostMapping("/momo/confirm")
    public ResponseEntity<Void> confirmMomoPayment(@RequestBody Map<String, Object> payload) {
        System.out.println("MoMo IPN Payload: " + payload);

        // 1. Kiểm tra mã kết quả (resultCode = 0 là thành công)
        Object resultCodeObj = payload.get("resultCode");
        if (resultCodeObj != null && "0".equals(resultCodeObj.toString())) {
            String orderReference = (String) payload.get("orderId");
            // Lấy ID đơn hàng gốc trước ký tự "TS"
            Long orderId = Long.parseLong(orderReference.split("TS")[0]);
            updateOrderAsPaid(orderId);
        }

        // Trả về 204 No Content cho MoMo
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint hỗ trợ Frontend đồng bộ trạng thái khi quay lại từ MoMo
     * (Hữu ích khi IPN không tới được localhost)
     */
    @GetMapping("/momo/sync")
    public ApiResponse<Boolean> syncPayment(@RequestParam Long orderId, @RequestParam int resultCode) {
        if (resultCode == 0) {
            updateOrderAsPaid(orderId);
            return ApiResponse.success(true);
        }
        return ApiResponse.error(400, "Thanh toán không thành công");
    }

    private void updateOrderAsPaid(Long orderId) {
        Orders order = orderService.getById(orderId);
        if (order != null && !"PAID".equals(order.getPaymentStatus())) {
            order.setPaymentStatus("PAID");
            order.setStatus(OrderStatus.CONFIRMED);
            orderService.getOrderRepository().save(order);

            // Thông báo Realtime cho Frontend qua SSE
            sseService.sendToAll(Map.of(
                "orderId", order.getId(),
                "status", "PAID",
                "message", "MoMo payment successful"
            ));
        }
    }
}

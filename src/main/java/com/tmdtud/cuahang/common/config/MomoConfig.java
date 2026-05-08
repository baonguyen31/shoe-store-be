package com.tmdtud.cuahang.common.config;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Formatter;

public class MomoConfig {
    // === CẤU HÌNH MOMO SANDBOX (OFFICIAL API) ===
    public static final String PARTNER_CODE = "MOMOBKUN20180529";
    public static final String ACCESS_KEY = "klm05TvNBzhg7h7j";
    public static final String SECRET_KEY = "at67qH6mk8w5Y1nAyMoYKMWACiEi2bsa";
    public static final String ENDPOINT = "https://test-payment.momo.vn/v2/gateway/api/create";
    
    // Khi deploy lên Render/Railway, hãy đổi domain này thành domain thật của bạn
    public static final String REDIRECT_URL = "http://localhost:3000/orders"; 
    public static final String NOTIFY_URL = "https://punk-caucasian-hangnail.ngrok-free.dev/api/payment/momo/confirm";

    // === THÔNG TIN TÀI KHOẢN CÁ NHÂN (VIETQR - DÙNG CHO QUÉT MÃ NHANH) ===
    public static final String MOMO_PHONE = "0769505807";
    public static final String MOMO_ACCOUNT_NAME = "Hồ Quốc Đạt";
    
    public static String getQrUrl(long amount, String orderId) {
        String addInfo = "THANH TOAN DON HANG " + orderId;
        String encodedName = "HO%20QUOC%20DAT";
        return String.format("https://img.vietqr.io/image/momo-%s-compact2.jpg?amount=%d&addInfo=%s&accountName=%s", 
                             MOMO_PHONE, amount, addInfo.replace(" ", "%20"), encodedName);
    }

    // === HÀM TẠO CHỮ KÝ HMAC SHA256 ===
    public static String hmacSha256(String data, String key) {
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            
            byte[] bytes = sha256_HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8));
            
            StringBuilder hash = new StringBuilder();
            for (byte b : bytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hash.append('0');
                hash.append(hex);
            }
            return hash.toString();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi tạo chữ ký HMAC SHA256", e);
        }
    }
}

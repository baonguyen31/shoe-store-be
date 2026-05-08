package com.tmdtud.cuahang.api.cart;

import com.tmdtud.cuahang.api.auth.model.CustomerDetails;
import com.tmdtud.cuahang.api.cart.dto.CartAddRequest;
import com.tmdtud.cuahang.api.cart.dto.CartUpdateRequest;
import com.tmdtud.cuahang.api.cart.model.CartItem;
import com.tmdtud.cuahang.api.cart.service.CartService;
import com.tmdtud.cuahang.common.construct.BaseController;
import com.tmdtud.cuahang.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/cart")
@RequiredArgsConstructor
public class CartController extends BaseController {
    private final CartService cartService;

    private Long getCurrentCustomerId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            throw new RuntimeException("Chưa đăng nhập");
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomerDetails) {
            return ((CustomerDetails) principal).getCustomer().getId();
        } else {
            throw new RuntimeException("Chỉ khách hàng mới có thể sử dụng giỏ hàng");
        }
    }

    @GetMapping
    public ApiResponse<List<CartItem>> getCart() {
        return ApiResponse.success(cartService.getCartByCustomer(getCurrentCustomerId()));
    }

    @PostMapping
    public ApiResponse<CartItem> addToCart(@RequestBody CartAddRequest request) {
        return ApiResponse.success(cartService.addToCart(
                getCurrentCustomerId(),
                request.getProductId(),
                request.getQuantity(),
                request.getSize(),
                request.getColor()
        ));
    }

    @PutMapping("/{id}")
    public ApiResponse<CartItem> updateQuantity(@PathVariable Long id, @RequestBody CartUpdateRequest request) {
        return ApiResponse.success(cartService.updateQuantity(getCurrentCustomerId(), id, request.getQuantity()));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> removeFromCart(@PathVariable Long id) {
        cartService.removeFromCart(getCurrentCustomerId(), id);
        return ApiResponse.success(null);
    }

    @DeleteMapping("/clear")
    public ApiResponse<Void> clearCart() {
        cartService.clearCart(getCurrentCustomerId());
        return ApiResponse.success(null);
    }
}

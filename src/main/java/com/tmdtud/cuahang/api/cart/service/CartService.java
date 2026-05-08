package com.tmdtud.cuahang.api.cart.service;

import com.tmdtud.cuahang.api.cart.model.CartItem;
import com.tmdtud.cuahang.api.cart.repository.CartRepository;
import com.tmdtud.cuahang.api.customer.model.Customers;
import com.tmdtud.cuahang.api.customer.repository.CustomerRepository;
import com.tmdtud.cuahang.api.product.model.Products;
import com.tmdtud.cuahang.api.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final com.tmdtud.cuahang.api.product.repository.ProductVariantRepository variantRepo;

    public List<CartItem> getCartByCustomer(Long customerId) {
        Customers customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));
        return cartRepository.findByCustomer(customer);
    }

    @Transactional
    public CartItem addToCart(Long customerId, Long productId, int quantity, String size, String color) {
        Customers customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));
        Products product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        // Validate stock
        Optional<com.tmdtud.cuahang.api.product.model.ProductVariant> variant = variantRepo.findByProductAndColorAndSize(product, color, size);
        if (variant.isPresent()) {
            if (variant.get().getQuantity() < quantity) {
                throw new RuntimeException("Số lượng sản phẩm trong kho không đủ (Chỉ còn " + variant.get().getQuantity() + " sản phẩm)");
            }
        } else {
            // If variants exist for this product but not this specific combo, it might be an error or legacy product
            List<com.tmdtud.cuahang.api.product.model.ProductVariant> variants = variantRepo.findByProduct(product);
            if (!variants.isEmpty()) {
                throw new RuntimeException("Biến thể màu/size này không tồn tại cho sản phẩm này");
            }
            // Fallback for legacy products without variants
            if (product.getQuantity() < quantity) {
                throw new RuntimeException("Sản phẩm đã hết hàng");
            }
        }

        Optional<CartItem> existingItem = cartRepository.findByCustomerAndProductAndSizeAndColor(customer, product, size, color);

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            int newQuantity = item.getQuantity() + quantity;
            // Check stock for total quantity
            if (variant.isPresent() && variant.get().getQuantity() < newQuantity) {
                throw new RuntimeException("Tổng số lượng trong giỏ hàng vượt quá tồn kho");
            }
            item.setQuantity(newQuantity);
            return cartRepository.save(item);
        } else {
            CartItem newItem = CartItem.builder()
                    .customer(customer)
                    .product(product)
                    .quantity(quantity)
                    .size(size)
                    .color(color)
                    .build();
            return cartRepository.save(newItem);
        }
    }

    @Transactional
    public CartItem updateQuantity(Long customerId, Long cartItemId, int quantity) {
        CartItem item = cartRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy mục trong giỏ hàng"));

        if (!item.getCustomer().getId().equals(customerId)) {
            throw new RuntimeException("Bạn không có quyền chỉnh sửa mục này");
        }

        // Validate stock
        Optional<com.tmdtud.cuahang.api.product.model.ProductVariant> variant = variantRepo.findByProductAndColorAndSize(item.getProduct(), item.getColor(), item.getSize());
        if (variant.isPresent() && variant.get().getQuantity() < quantity) {
            throw new RuntimeException("Số lượng tồn kho không đủ (Chỉ còn " + variant.get().getQuantity() + " sản phẩm)");
        }

        item.setQuantity(quantity);
        if (item.getQuantity() <= 0) {
            cartRepository.delete(item);
            return null;
        }
        return cartRepository.save(item);
    }

    @Transactional
    public void removeFromCart(Long customerId, Long cartItemId) {
        CartItem item = cartRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy mục trong giỏ hàng"));

        if (!item.getCustomer().getId().equals(customerId)) {
            throw new RuntimeException("Bạn không có quyền xóa mục này");
        }

        cartRepository.delete(item);
    }

    @Transactional
    public void clearCart(Long customerId) {
        Customers customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));
        cartRepository.deleteByCustomer(customer);
    }
}

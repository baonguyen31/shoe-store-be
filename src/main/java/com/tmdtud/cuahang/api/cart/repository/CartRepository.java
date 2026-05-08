package com.tmdtud.cuahang.api.cart.repository;

import com.tmdtud.cuahang.api.cart.model.CartItem;
import com.tmdtud.cuahang.api.customer.model.Customers;
import com.tmdtud.cuahang.api.product.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCustomer(Customers customer);
    Optional<CartItem> findByCustomerAndProductAndSizeAndColor(Customers customer, Products product, String size, String color);
    void deleteByCustomer(Customers customer);
}

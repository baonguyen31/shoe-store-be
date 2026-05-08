package com.tmdtud.cuahang.api.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;

import com.tmdtud.cuahang.api.product.model.Products;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {

    @EntityGraph(attributePaths = { "category", "brand", "variants" })
    Optional<Products> findById(Long id);

    @EntityGraph(attributePaths = { "category", "brand", "variants" })
    org.springframework.data.domain.Page<Products> findAll(org.springframework.data.domain.Pageable pageable);

    @EntityGraph(attributePaths = { "category", "brand", "variants" })
    org.springframework.data.domain.Page<Products> findByNameContainingIgnoreCase(String name, org.springframework.data.domain.Pageable pageable);

    @EntityGraph(attributePaths = { "category", "brand", "variants" })
    org.springframework.data.domain.Page<Products> findByCategoryId(Long categoryId, org.springframework.data.domain.Pageable pageable);

    @EntityGraph(attributePaths = { "category", "brand", "variants" })
    org.springframework.data.domain.Page<Products> findByNameContainingIgnoreCaseAndCategoryId(String name, Long categoryId, org.springframework.data.domain.Pageable pageable);

    @EntityGraph(attributePaths = { "category", "brand", "variants" })
    List<Products> findAll();

    @EntityGraph(attributePaths = { "category", "brand", "variants" })
    @Query("SELECT DISTINCT p FROM Products p LEFT JOIN p.variants v WHERE " +
           "(:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:categoryId IS NULL OR p.category.id = :categoryId) AND " +
           "(:brandId IS NULL OR p.brand.id = :brandId) AND " +
           "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
           "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
           "(:color IS NULL OR LOWER(v.color) LIKE LOWER(CONCAT('%', :color, '%')))")
    org.springframework.data.domain.Page<Products> findProductsWithFilters(
        @Param("name") String name, 
        @Param("categoryId") Long categoryId, 
        @Param("brandId") Long brandId, 
        @Param("minPrice") BigDecimal minPrice, 
        @Param("maxPrice") BigDecimal maxPrice, 
        @Param("color") String color,
        org.springframework.data.domain.Pageable pageable);

    @EntityGraph(attributePaths = { "category", "brand", "variants" })
    @Query("SELECT p FROM Products p JOIN OrdersDetails od ON p.id = od.product.id " +
           "GROUP BY p.id " +
           "ORDER BY SUM(od.quantity) DESC")
    org.springframework.data.domain.Page<Products> findBestSellers(org.springframework.data.domain.Pageable pageable);

    @EntityGraph(attributePaths = { "category", "brand" }) // Chỉ giữ lại category và brand
    @Query("SELECT p FROM Products p " +
            "JOIN OrdersDetails od ON p.id = od.product.id " +
            "WHERE (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:categoryId IS NULL OR p.category.id = :categoryId) AND " +
            "(:brandId IS NULL OR p.brand.id = :brandId) " +
            "GROUP BY p.id, p.name, p.price, p.quantity, p.description, p.imageUrl, " +
            "p.discountPercentage, p.rating, p.deleted, p.category.id, p.brand.id " +
            "ORDER BY SUM(od.quantity) DESC")
    Page<Products> findBestSellersWithFilters(
        @Param("name") String name, 
        @Param("categoryId") Long categoryId, 
        @Param("brandId") Long brandId, 
        @Param("minPrice") BigDecimal minPrice, 
        @Param("maxPrice") BigDecimal maxPrice, 
        @Param("color") String color,
        Pageable pageable);

    @Modifying
    @Query(value = "UPDATE products SET category_id = NULL, brand_id = NULL WHERE category_id = ?1", nativeQuery = true)
    int setDefaultCategoryAndBrandByCategory(Long categoryId);

    @Modifying
    @Query(value = "UPDATE products SET brand_id = NULL WHERE brand_id = ?1", nativeQuery = true)
    int setDefaultBrand(Long brandId);


}

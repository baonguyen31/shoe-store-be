package com.tmdtud.cuahang.api.brand.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tmdtud.cuahang.api.brand.model.Brands;

@Repository
public interface BrandRepo extends JpaRepository<Brands, Long>{

    @Modifying
    @Query(value = "UPDATE brands SET category_id = 4 WHERE category_id = ?1", nativeQuery = true)
    @Transactional
    int setDefaultCategory(Long id);

}

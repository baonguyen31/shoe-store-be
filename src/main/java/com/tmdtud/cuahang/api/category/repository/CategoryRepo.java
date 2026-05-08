package com.tmdtud.cuahang.api.category.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tmdtud.cuahang.api.category.model.Categories;

@Repository
public interface CategoryRepo extends JpaRepository<Categories, Long>{

    
}

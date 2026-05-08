package com.tmdtud.cuahang.api.category.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmdtud.cuahang.api.brand.service.BrandService;
import com.tmdtud.cuahang.api.category.model.Categories;
import com.tmdtud.cuahang.api.category.repository.CategoryRepo;
import com.tmdtud.cuahang.api.category.request.CategoryStoreRequest;
import com.tmdtud.cuahang.api.category.request.CategoryUpdateRequest;
import com.tmdtud.cuahang.api.product.service.ProductService;
import com.tmdtud.cuahang.common.response.PageResponse;



@Service
public class CategoryService implements CategoryServiceI {

    @Autowired
    private CategoryRepo categoryRepo;
    
    @Lazy
    @Autowired
    private BrandService brandService;

    @Lazy
    @Autowired
    private ProductService productService;

    @Override
    public Categories add(CategoryStoreRequest request) {
        Categories categories = Categories.builder()
                                    .name(request.getName()).build();
        return categoryRepo.save(categories);
    }

    @Override
    @Transactional
    public Boolean delete(Long id) {
        productService.setCategoryAndBrandDefaultByCategory(id);
        brandService.setDefaultCategory(id);
        categoryRepo.deleteById(id);
        return true;
    }

    @Override
    public PageResponse<Categories> getAll(Pageable pageable) {
        Page<Categories> categories = categoryRepo.findAll(pageable);
        return new PageResponse<Categories>(categories.map(category -> category));
    }

    @Override
    public Categories getById(Long id) {
        return categoryRepo.findById(id).orElse(null);
    }

    @Override
    public Categories update(CategoryUpdateRequest request) {
        Categories categories = categoryRepo.findById(request.getId()).orElse(null);

        categories.setName(request.getName());
        return categories;
    }
    
}

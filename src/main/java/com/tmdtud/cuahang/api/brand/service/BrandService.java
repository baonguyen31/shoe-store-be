package com.tmdtud.cuahang.api.brand.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmdtud.cuahang.api.brand.model.Brands;
import com.tmdtud.cuahang.api.brand.repository.BrandRepo;
import com.tmdtud.cuahang.api.brand.request.BrandStoreRequest;
import com.tmdtud.cuahang.api.brand.request.BrandUpdateRequest;
import com.tmdtud.cuahang.api.category.model.Categories;
import com.tmdtud.cuahang.api.category.service.CategoryService;
import com.tmdtud.cuahang.api.product.service.ProductService;
import com.tmdtud.cuahang.common.response.PageResponse;


@Service
public class BrandService implements BrandServiceI {

    @Autowired
    private BrandRepo brandRepo;

    @Lazy
    @Autowired
    private CategoryService categoryService;

    @Lazy
    @Autowired
    private ProductService productService;

    @Override
    public Brands add(BrandStoreRequest request) {
        Categories category = categoryService.getById(request.getCategory_id());
        Brands brand = Brands.builder()
                        .category(category)
                        .name(request.getName()).build();
        return brandRepo.save(brand);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        productService.setDefaultBrand(id);
        brandRepo.deleteById(id);
        return true;
    }

    @Override
    public PageResponse<Brands> getAll(Pageable pageable) {
        Page<Brands> brands = brandRepo.findAll(pageable);
        return new PageResponse<Brands>(brands.map(brand -> brand));
    }

    @Override
    public Brands getById(Long id) {
        return brandRepo.findById(id).orElse(null);
    }

    @Override
    public Brands update(BrandUpdateRequest request) {
        Categories category = categoryService.getById(request.getCategory_id());
        Brands brand = brandRepo.findById(request.getId()).orElse(null);

        brand.setCategory(category);
        brand.setName(request.getName());
        return brandRepo.save(brand);
    }

    @Override
    public int setDefaultCategory(Long categoryId){
        return brandRepo.setDefaultCategory(categoryId);
    }

}

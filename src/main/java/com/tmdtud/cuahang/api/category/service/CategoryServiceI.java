package com.tmdtud.cuahang.api.category.service;

import org.springframework.data.domain.Pageable;

import com.tmdtud.cuahang.api.category.model.Categories;
import com.tmdtud.cuahang.api.category.request.CategoryStoreRequest;
import com.tmdtud.cuahang.api.category.request.CategoryUpdateRequest;
import com.tmdtud.cuahang.common.response.PageResponse;

public interface CategoryServiceI {
    public PageResponse<Categories> getAll(Pageable pageable);
    public Categories getById(Long id);
    public Boolean delete(Long id);
    public Categories update(CategoryUpdateRequest request);
    public Categories add(CategoryStoreRequest request);
}

package com.tmdtud.cuahang.api.brand.service;


import org.springframework.data.domain.Pageable;

import com.tmdtud.cuahang.api.brand.model.Brands;
import com.tmdtud.cuahang.api.brand.request.BrandStoreRequest;
import com.tmdtud.cuahang.api.brand.request.BrandUpdateRequest;
import com.tmdtud.cuahang.common.response.PageResponse;

public interface BrandServiceI {
    public PageResponse<Brands> getAll(Pageable pageable);

    public Brands getById(Long id);

    public boolean delete(Long id);

    public Brands update(BrandUpdateRequest request);

    public Brands add(BrandStoreRequest request);

    public int setDefaultCategory(Long categoryId);
}

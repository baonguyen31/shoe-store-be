package com.tmdtud.cuahang.api.employer.service;


import org.springframework.data.domain.Pageable;

import com.tmdtud.cuahang.api.employer.dto.EmployerDTO;
import com.tmdtud.cuahang.api.employer.model.Employers;
import com.tmdtud.cuahang.common.response.PageResponse;


public interface EmployerServiceI {
    public PageResponse<EmployerDTO> getAll(Pageable pageable);
    public Employers getById(Long id);
    public void delete(Long id);
    public void add(EmployerDTO employer);
    public void update(EmployerDTO employer);
}

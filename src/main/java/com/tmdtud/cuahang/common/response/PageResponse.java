package com.tmdtud.cuahang.common.response;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {
    List<T> content;
    int num;
    int size;
    long total;

    public PageResponse(Page<T> page){
        content = page.getContent();
        num = page.getPageable().getPageNumber();
        size = page.getPageable().getPageSize();
        total = page.getTotalElements();
    }

}

package com.tmdtud.cuahang.common.construct;

import com.tmdtud.cuahang.common.response.ApiResponse;

public abstract class BaseController {

    protected <T> ApiResponse<T> createSuccessReponse(T data){
        return ApiResponse.success(data);
    }
    
}

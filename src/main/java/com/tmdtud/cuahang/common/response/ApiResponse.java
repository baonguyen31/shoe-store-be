package com.tmdtud.cuahang.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private int code;
    private String message;
    private T result;
    
    public static <T> ApiResponse<T> success(T result){
        return new ApiResponse<T>(200, "Success", result);
    }

    public static <T> ApiResponse<T> error(int code, String message){
        return new ApiResponse<T>(code, message, null);
    }

    public static <T> ApiResponse<T> errorMessageList(int code, String message, T result){
        return new ApiResponse<T>(code, message, result);
    }
}

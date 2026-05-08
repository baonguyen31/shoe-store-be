package com.tmdtud.cuahang.common.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.tmdtud.cuahang.common.response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handlerValidationException(
        MethodArgumentNotValidException ex){
            Map<String, String> errors = new HashMap<>();

            ex.getBindingResult().getFieldErrors().forEach(error -> {
                errors.put(error.getField(), error.getDefaultMessage());
            });

            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.errorMessageList(400, "Miss data", errors));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<String>> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex) {

        String message = "Parameter '" + ex.getName() + "' must be a number";

        return ResponseEntity.badRequest()
                .body(ApiResponse.error(400, message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleAllExceptions(Exception ex){
        String message = ex.getMessage();
        int status = 400;
        
        if (message != null && (message.contains("Chưa đăng nhập") || message.contains("Full authentication is required"))) {
            status = 401;
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error(status, message));
        }
        
        return ResponseEntity.status(status).body(ApiResponse.error(status, message));
    }
}

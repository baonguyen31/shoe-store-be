package com.tmdtud.cuahang.api.customer.request;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerStoreRequest {
    @NotEmpty(message = "Tên không được để trống")
    private String fullName;

    @NotEmpty(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotEmpty(message = "Số điện thoại không được để trống")
    private String phone;

    @NotNull(message = "Ngày sinh không được để trống")
    private String dateOfBirth;
}

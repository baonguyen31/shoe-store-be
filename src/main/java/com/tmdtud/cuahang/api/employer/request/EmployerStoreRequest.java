package com.tmdtud.cuahang.api.employer.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployerStoreRequest {
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

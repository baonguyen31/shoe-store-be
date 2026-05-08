package com.tmdtud.cuahang.common.dto;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @NotNull(message = "id not null")
    Long id;

    @NotBlank(message = "username Not empty")
    @NotNull(message = "username Not null")
    private String username;

    @NotBlank(message = "fullName Not empty")
    @NotNull(message = "fullName Not null")
    private String fullName;

    @NotBlank(message = "email Not empty")
    @NotNull(message = "email Not null")
    @Email(message = "email Not valid")
    private String email;

    @NotBlank(message = "phone Not empty")
    @Pattern(regexp = "^[0-9]{10,11}$", message = "phone Not valid")
    private String phone;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @NotBlank(message = "Mật khẩu không được để trống")
    @NotNull(message = "Mật khẩu không được để trống")
    private String password;

    private Integer status;

    private String city;

    private String ward;

    private String street;

    private boolean resetRequested = false;

}

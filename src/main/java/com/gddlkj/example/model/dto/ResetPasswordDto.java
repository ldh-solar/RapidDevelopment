package com.gddlkj.example.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ResetPasswordDto {

    private String confirmPassword;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度只能在6-20之间")
    private String oldPassword;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度只能在6-20之间")
    private String password;
}

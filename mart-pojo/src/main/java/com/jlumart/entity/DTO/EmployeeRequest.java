package com.jlumart.entity.DTO;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class EmployeeRequest {
    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "昵称不能为空")
    private String name;

    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    @Email(message = "邮箱格式不正确")
    private String email;

    private String avatar;

    @NotNull(message = "性别不能为空")
    @Min(value = 1, message = "性别值不合法")
    @Max(value = 2, message = "性别值不合法")
    private Integer sex;
}

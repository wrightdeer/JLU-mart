package com.jlumart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用登录参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
}

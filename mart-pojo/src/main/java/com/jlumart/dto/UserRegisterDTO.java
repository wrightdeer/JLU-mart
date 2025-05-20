package com.jlumart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户注册
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDTO {
    /**
     * 用户昵称
     */
    private String name;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户名
     */
    private String username;
}

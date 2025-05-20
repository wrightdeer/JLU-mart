package com.jlumart.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 员工登录
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeLoginVO {
    /**
     * 头像url
     */
    private String avatar;
    /**
     * id
     */
    private long id;
    /**
     * 昵称
     */
    private String name;
    /**
     * jwt令牌
     */
    private String token;
    /**
     * 用户名
     */
    private String username;
}

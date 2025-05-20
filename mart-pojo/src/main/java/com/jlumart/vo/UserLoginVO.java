package com.jlumart.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户登录
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginVO {
    /**
     * 头像url
     */
    private String avatar;
    /**
     * 余额
     */
    private Double balance;
    /**
     * 用户id
     */
    private Long id;
    /**
     * 用户昵称
     */
    private String name;
    /**
     * jwt令牌
     */
    private String token;
    /**
     * 用户用户名
     */
    private String username;
}

package com.jlumart.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 配送员登录
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourierLoginVO {
    /**
     * 头像url
     */
    private String avatar;
    /**
     * 配送员ID
     */
    private Long id;
    /**
     * 配送员昵称
     */
    private String name;
    /**
     * jwt令牌
     */
    private String token;
    /**
     * 配送员用户名
     */
    private String username;
}

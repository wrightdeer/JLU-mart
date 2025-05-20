package com.jlumart.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVO {
    /**
     * 头像url
     */
    private String avatar;
    /**
     * 余额
     */
    private Double balance;
    /**
     * id
     */
    private Long id;
    /**
     * 昵称
     */
    private String name;
    /**
     * 用户名
     */
    private String username;
}

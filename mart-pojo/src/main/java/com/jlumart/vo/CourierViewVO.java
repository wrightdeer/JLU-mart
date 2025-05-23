package com.jlumart.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 配送员信息
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourierViewVO {
    /**
     * 头像url
     */
    private String avatar;
    /**
     * 余额
     */
    private Double balance;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 配送员id
     */
    private Long id;
    /**
     * 昵称
     */
    private String name;
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 1 男 2 女
     */
    private Integer sex;
    /**
     * 配送员用户名
     */
    private String username;
}

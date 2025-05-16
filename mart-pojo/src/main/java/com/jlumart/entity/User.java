package com.jlumart.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    /**
     * 用户id
     */
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户昵称
     */
    private String name;
    /**
     * 密码
     */
    private String password;
    /**
     * 性别 1 男 2 女
     */
    private Integer sex;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 余额
     */
    private Double balance;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 登录频次
     */
    private Long loginCount;
    /**
     * 请求频次
     */
    private Long requestCount;
    /**
     * 最后访问时间
     */
    private LocalDateTime lastRequestTime;
    /**
     * 用户活跃天数
     */
    private Long activeDays;
    /**
     * 购物次数
     */
    private Long orderCount;
    /**
     * 购物总额
     */
    private Double totalSpent;
    /**
     * 最后下单时间
     */
    private LocalDateTime lastOrderTime;
    /**
     * 取消订单次数
     */
    private Long cancelCount;
    /**
     * 取消订单总额
     */
    private Double cancelValue;
    /**
     * 最后取消订单时间
     */
    private LocalDateTime lastCancelTime;

}

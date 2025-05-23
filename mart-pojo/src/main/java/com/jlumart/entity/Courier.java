package com.jlumart.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Courier {
    /**
     * 配送员id
     */
    private Long id;
    /**
     * 配送员头像
     */
    private String avatar;
    /**
     * 配送员邮箱
     */
    private String email;
    /**
     * 配送员名称
     */
    private String name;
    /**
     * 配送员手机号
     */
    private String phone;
    /**
     * 配送员性别
     */
    private Integer sex;
    /**
     * 配送员状态
     */
    private Integer status;
    /**
     * 配送员用户名
     */
    private String username;
    /**
     * 配送员密码
     */
    private String password;
    /**
     * 配送员余额
     */
    private Double balance;
    /**
     * 配送员创建时间
     */
    private LocalDateTime  createTime;
    /**
     * 配送员更新时间
     */
    private LocalDateTime  updateTime;
}

package com.jlumart.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserVO {
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
     * 用户id
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
     * 性别，1 男 2 女
     */
    private Integer sex;
    /**
     * 用户名
     */
    private String username;
}

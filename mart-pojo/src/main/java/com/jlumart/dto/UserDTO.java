package com.jlumart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 修改用户
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    /**
     * 邮箱
     */
    private String email;
    /**
     * 头像url
     */
    private String image;
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

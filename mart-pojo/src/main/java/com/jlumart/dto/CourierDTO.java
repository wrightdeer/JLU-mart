package com.jlumart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 新增/修改快递员
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourierDTO {
    /**
     * id
     */
    private Long id;
    /**
     * 头像url
     */
    private String avatar;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 昵称
     */
    private String name;
    /**
     * 电话号码，11位数字
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

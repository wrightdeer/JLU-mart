package com.jlumart.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeVO {
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
     * 电话号码
     */
    private String phone;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 用户名
     */
    private String username;
}

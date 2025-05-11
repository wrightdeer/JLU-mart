package com.jlumart.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeePageVO {
    /**
     * 头像url
     */
    private String avatar;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 员工id
     */
    private Long id;
    /**
     * 昵称
     */
    private String name;
    /**
     * 电话
     */
    private String phone;
    /**
     * 性别
     */
    private Long sex;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 更新时间，精确到秒
     */
    private LocalDateTime updateTime;
    /**
     * 用户名
     */
    private String username;
}

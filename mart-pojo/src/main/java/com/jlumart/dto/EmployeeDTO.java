package com.jlumart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

/**
 * 新增/修改员工信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDTO {
    /**
     * 员工id
     */
    private Long id;
    /**
     * 员工用户名
     */
    private String username;
    /**
     * 员工姓名
     */
    private String name;
    /**
     * 员工手机号
     */
    private String phone;
    /**
     * 员工邮箱
     */
    private String email;
    /**
     * 员工头像
     */
    private String avatar;
    /**
     * 员工性别
     */
    private Integer sex;
}

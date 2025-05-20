package com.jlumart.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 员工信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeInfoVO {
    /**
     * 头像url
     */
    private String avatar;
    /**
     * id
     */
    private long id;
    /**
     * 昵称
     */
    private String name;
    /**
     * 用户名
     */
    private String username;
}

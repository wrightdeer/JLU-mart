package com.jlumart.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private Long id;            // 主键ID(自增)
    private String username;    // 用户名
    private String name;        // 昵称
    private String password;    // 密码(加密存储)
    private String phone;       // 手机号
    private String email;       // 邮箱
    private String avatar;      // 头像URL
    private Integer sex;        // 性别 1-男 2-女
    private Integer status;     // 状态 0-禁用 1-启用
    private Long createUser;    // 创建人ID
    private Long updateUser;    // 更新人ID
    private LocalDateTime createTime;  // 创建时间
    private LocalDateTime updateTime;  // 更新时间
}
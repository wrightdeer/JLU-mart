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
    /**
     * 主键ID(自增)
     */
    private Long id;            

    /**
     * 用户名
     */
    private String username;    

    /**
     * 昵称
     */
    private String name;        

    /**
     * 密码(加密存储)
     */
    private String password;    

    /**
     * 手机号
     */
    private String phone;       

    /**
     * 邮箱
     */
    private String email;       

    /**
     * 头像URL
     */
    private String avatar;      

    /**
     * 性别 1-男 2-女
     */
    private Integer sex;

    /**
     * 状态 0-禁用 1-启用
     */
    private Integer status;

    /**
     * 创建人ID
     */
    private Long createUser;    

    /**
     * 更新人ID
     */
    private Long updateUser;    

    /**
     * 创建时间
     */
    private LocalDateTime createTime;  

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;  
}
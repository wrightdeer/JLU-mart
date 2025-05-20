package com.jlumart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用修改密码参数
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordDTO {
    /**
     * 原密码
     */
    private String oldPassword;
    /**
     * 新密码
     */
    private String newPassword;
}

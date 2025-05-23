package com.jlumart.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 配送员信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourierInfoVO {
    /**
     * 头像url
     */
    private String avatar;
    /**
     * id
     */
    private Long id;
    /**
     * 昵称
     */
    private String name;
    /**
     * 用户名
     */
    private String username;
}

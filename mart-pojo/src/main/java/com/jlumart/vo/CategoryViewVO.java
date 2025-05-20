package com.jlumart.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户端显示的分类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryViewVO {
    /**
     * 分类id
     */
    private Long id;
    /**
     * 分类名称
     */
    private String name;
}

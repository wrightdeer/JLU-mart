package com.jlumart.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 根据父分类id查询子分类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryByParentVO {
    /**
     * 分类id
     */
    private Long id;
    /**
     * 分类名
     */
    private String name;
    /**
     * 状态
     */
    private Integer status;
}

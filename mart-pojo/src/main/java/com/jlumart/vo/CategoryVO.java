package com.jlumart.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * id查询分类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryVO {
    /**
     * 分类名
     */
    private String name;
    /**
     * 父分类id
     */
    private Long parentId;
    /**
     * 排序权重
     */
    private Long sortWeight;
}

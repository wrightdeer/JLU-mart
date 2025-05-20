package com.jlumart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 新增/修改分类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO {
    /**
     * 分类id
     */
    private Long id;
    /**
     * 分类名称
     */
    private String name;
    /**
     * 父级分类id
     */
    private Long parentId;
    /**
     * 排序权重
     */
    private Long sortWeight;
}

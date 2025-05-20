package com.jlumart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分类分页参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryPageDTO {
    /**
     * 页码
     */
    private Integer page;
    /**
     * 页面大小
     */
    private Integer pageSize;
    /**
     * 分类名称
     */
    private String name;
    /**
     * 分类状态
     */
    private Integer status;
    /**
     * 父级分类
     */
    private Long parentId;
}

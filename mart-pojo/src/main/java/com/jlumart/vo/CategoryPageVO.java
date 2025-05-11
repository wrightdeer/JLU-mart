package com.jlumart.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryPageVO {
    /**
     * 分类id
     */
    private Long id;
    /**
     * 分类名
     */
    private String name;
    /**
     * 父分类名
     */
    private String parentCategory;
    /**
     * 父分类id
     */
    private Long parentId;
    /**
     * 分类权重
     */
    private Long sortWeight;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}

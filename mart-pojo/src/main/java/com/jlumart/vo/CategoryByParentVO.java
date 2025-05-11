package com.jlumart.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

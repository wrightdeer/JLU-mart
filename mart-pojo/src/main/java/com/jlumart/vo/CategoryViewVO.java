package com.jlumart.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

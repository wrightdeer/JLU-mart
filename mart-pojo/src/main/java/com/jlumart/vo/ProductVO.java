package com.jlumart.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductVO {
    /**
     * 分类id
     */
    private Long categoryId;
    /**
     * 商品描述
     */
    private String description;
    /**
     * 图片url
     */
    private String image;
    /**
     * 商品名
     */
    private String name;
    /**
     * 父分类id
     */
    private Long parentCategoryId;
    /**
     * 商品价格
     */
    private Double price;
}

package com.jlumart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 新增/修改商品
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    /**
     * 商品id
     */
    private Long id;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品描述
     */
    private String description;
    /**
     * 商品价格
     */
    private Double price;
    /**
     * 商品图片
     */
    private String image;
    /**
     * 商品分类id
     */
    private Long categoryId;
}

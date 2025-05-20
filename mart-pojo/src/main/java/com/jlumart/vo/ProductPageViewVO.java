package com.jlumart.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户浏览商品分页
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductPageViewVO {
    /**
     * 商品id
     */
    private Long id;
    /**
     * 图片url
     */
    private String image;
    /**
     * 商品名
     */
    private String name;
    /**
     * 商品价格
     */
    private Double price;
    /**
     * 商品库存
     */
    private Long stock;
}

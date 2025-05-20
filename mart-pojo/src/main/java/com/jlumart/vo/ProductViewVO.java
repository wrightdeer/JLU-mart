package com.jlumart.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户浏览商品
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductViewVO {
    /**
     * 该商品在用户购物车的数量
     */
    private Long cartQuantity;
    /**
     * 分类名
     */
    private String categoryName;
    /**
     * 商品描述
     */
    private String description;
    /**
     * 图片url
     */
    private String image;
    /**
     * 该商品是否被用户收藏，0 否 1 是
     */
    private Integer isFavorite;
    /**
     * 商品名
     */
    private String name;
    /**
     * 父分类名
     */
    private String parentCategoryName;
    /**
     * 商品价格
     */
    private Double price;
    /**
     * 商品库存
     */
    private Long stock;
}

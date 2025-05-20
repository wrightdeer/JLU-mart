package com.jlumart.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 商品分页
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductPageVO implements Serializable {
    /**
     * 分类名
     */
    private String categoryName;
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
     * 父分类名
     */
    private String parentCategoryName;
    /**
     * 商品价格
     */
    private Double price;
    /**
     * 商品状态
     */
    private Integer status;
    /**
     * 商品库存
     */
    private Long stock;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}

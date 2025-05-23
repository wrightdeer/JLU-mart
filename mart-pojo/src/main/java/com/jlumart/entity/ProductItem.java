package com.jlumart.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductItem {
    /**
     * 商品项id
     */
    private Long id;
    /**
     * 商品数量
     */
    private Long quantity;
    /**
     * 商品价格
     */
    private Double price;
    /**
     * 商品名称
     */
    private String  name;
    /**
     * 商品图片
     */
    private String image;
}

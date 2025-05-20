package com.jlumart.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCart {
    /**
     * id
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 商品id
     */
    private Long productsId;
    /**
     * 商品名称
     */
    private String productsName;
    /**
     * 商品图片
     */
    private String productsImage;
    /**
     * 商品价格
     */
    private Double productsPrice;
    /**
     * 商品数量
     */
    private Long quantity;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}

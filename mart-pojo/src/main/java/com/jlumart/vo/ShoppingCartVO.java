package com.jlumart.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 购物车
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartVO {
    /**
     * 购物车id
     */
    private Long id;
    /**
     * 是否有效，0 无效 1 有效
     */
    private Integer isValid;
    /**
     * 商品id
     */
    private Long productsId;
    /**
     * 商品图片
     */
    private String productsImage;
    /**
     * 商品名
     */
    private String productsName;
    /**
     * 商品单价
     */
    private Double productsPrice;
    /**
     * 数量
     */
    private Long quantity;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}

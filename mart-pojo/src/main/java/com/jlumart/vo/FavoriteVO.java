package com.jlumart.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 收藏夹
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteVO {
    /**
     * 收藏夹id
     */
    private Long id;
    /**
     * 是否改变，0 否 1 是
     */
    private Integer isChange;
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
     * 更新时间
     */
    private LocalDateTime updateTime;
}

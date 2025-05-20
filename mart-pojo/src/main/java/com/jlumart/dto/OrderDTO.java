package com.jlumart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 创建购物订单
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {
    /**
     * 地址簿id
     */
    private Long addressBookId;
    /**
     * 购物车id列表，仅orderType为1时需要
     */
    private List<Long> cartIdList;
    /**
     * 订单类型，0 商品*数量 1 购物车
     */
    private Integer orderType;
    /**
     * 商品id，仅orderType为0时需要
     */
    private Long productId;
    /**
     * 数量，仅orderType为0时需要
     */
    private Long quantity;
    /**
     * 备注
     */
    private String remark;
}

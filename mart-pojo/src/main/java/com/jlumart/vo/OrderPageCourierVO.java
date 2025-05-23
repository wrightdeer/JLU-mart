package com.jlumart.vo;

import com.jlumart.entity.ProductItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 配送端订单分页
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderPageCourierVO {
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 配送距离，单位：米
     */
    private Long deliveryDistance;
    /**
     * 配送费
     */
    private Double deliveryFee;
    /**
     * 订单id
     */
    private Long id;
    /**
     * 商品详情
     */
    private List<ProductItem> items;
    /**
     * 订单号
     */
    private String orderId;
    /**
     * 状态，0 未支付 1 待配送 2 配送中 3 已完成 4 已取消
     */
    private Integer status;
}

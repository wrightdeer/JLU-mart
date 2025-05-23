package com.jlumart.vo;

import com.jlumart.entity.ProductItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 管理端订单分页
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderPageVO {
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
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
    /**
     * 总额
     */
    private Double totalAmount;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 用户名
     */
    private String username;
}

package com.jlumart.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单创建
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderPayVO {
    /**
     * 配送费
     */
    private Double deliveryFee;
    /**
     * 订单id
     */
    private String orderId;
    /**
     * 总金额
     */
    private Double totalAmount;
}

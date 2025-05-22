package com.jlumart.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单数量
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderStatisticVO {
    /**
     * 派送中数量
     */
    private Long deliveryInProgress;
    /**
     * 待派送数量
     */
    private Long toBeDelivery;
    /**
     * 待支付数量
     */
    private Long toBePay;
}

package com.jlumart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 购物确认支付
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderPayDTO {
    /**
     * 订单号
     */
    private String orderId;
    /**
     * 支付方式 
     */
    private Integer type;
}

package com.jlumart.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 充值订单创建
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentVO {
    private String paymentOrderId;
}

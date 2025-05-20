package com.jlumart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 确认支付
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDTO {
    private String paymentOrderId;
}

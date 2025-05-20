package com.jlumart.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 充值订单分页
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentPageVO {
    /**
     * 金额
     */
    private Double amount;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * id
     */
    private Long id;
    /**
     * 充值订单号
     */
    private String orderId;
    /**
     * 状态，0 未支付 1 已支付 2 已取消
     */
    private Integer status;
}

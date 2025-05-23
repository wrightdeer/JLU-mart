package com.jlumart.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentOrder {
    /**
     * 订单id
     */
    private Long id;
    /**
     * 订单号
     */
    private String orderId;
    /**
     * 用户id
     */
    private Long  userId;
    /**
     * 金额
     */
    private Double amount;
    /**
     * 订单状态
     */
    private Integer status;
    /**
     * 更新时间
     */
    private LocalDateTime createTime;
}

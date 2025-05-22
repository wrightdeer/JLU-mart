package com.jlumart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单取消
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderCancelDTO {
    /**
     * 订单id
     */
    private Long id;
    /**
     * 取消理由
     */
    private String reason;
    /**
     * 用户id
     */
    private Long userId;
}
